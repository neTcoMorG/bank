package com.youngjun.bank.web.controller;

import com.youngjun.bank.domain.dto.ProcessedHistory;
import com.youngjun.bank.domain.dto.TransferDTO;
import com.youngjun.bank.domain.entity.History;
import com.youngjun.bank.domain.entity.TradeType;
import com.youngjun.bank.domain.entity.User;
import com.youngjun.bank.domain.service.AccountValidUtil;
import com.youngjun.bank.domain.service.BankService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/bank")
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;
    private final AccountValidUtil validUtil;

    @GetMapping
    public String home(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        try {
            User user = bankService.getUserWithName(session.getAttribute("LOGIN").toString());
            model.addAttribute("AN", user.getAccountNumber());
            model.addAttribute("MONEY", user.getMoney());
            model.addAttribute("HISTORIES", processHistory(user));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        log.info(session.getAttribute("LOGIN").toString());
        return "bank";
    }

    @GetMapping("/add")
    public String add(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        bankService.jackpot(bankService.getUserWithName(session.getAttribute("LOGIN").toString()));
        return "redirect:/bank";
    }

    @GetMapping("/sendForm")    // 계좌 입력
    public String send(HttpServletRequest request) throws Exception {
        return "sendForm";
    }

    @GetMapping("/sendDetail") // 금액 입력
    public String sendDetail (@RequestParam String accountNumber) {
        try {
            validUtil.isValidWithAN(accountNumber);
        } catch (NoSuchElementException e) {
            return "fail";
        }
        return "sendDetail";
    }
    
    @PostMapping("/transfer")   // 최종 확인 및 전송
    @Transactional
    public String transfer(@RequestParam String accountNumber, @RequestParam Integer money, HttpServletRequest request) throws Exception {
        User user = bankService.getUserWithName(request.getSession(false).getAttribute("LOGIN").toString());
        bankService.transferTo(new TransferDTO(user, bankService.getUserWithAccount(accountNumber), money));
        return "redirect:/bank/done/" + accountNumber + "/" + money;
    }

    @GetMapping("/done/{accountNumber}/{money}")
    public String done (@PathVariable String accountNumber, @PathVariable Integer money, Model model, HttpServletRequest request) throws Exception {
        User user = bankService.getUserWithName(request.getSession(false).getAttribute("LOGIN").toString());
        User target = bankService.getUserWithAccount(accountNumber);

        model.addAttribute("TRANSFER", user);
        model.addAttribute("AN", accountNumber);
        model.addAttribute("MONEY", money);
        model.addAttribute("NAME", target.getUserName());

        return "done";
    }


    private List<ProcessedHistory> processHistory(User user) {
        List<History> rawHistory = bankService.getHistories(user);
        List<ProcessedHistory> result = new ArrayList<>();

        rawHistory.forEach(raw -> {
            if (raw.getReceiver().equals(user)) {
                result.add(new ProcessedHistory(raw, TradeType.RECV));
            }
            else {
                result.add(new ProcessedHistory(raw, TradeType.SEND));
            }
        });

        result.sort(new HistoryComparator().reversed());
        return result;
    }

    static class HistoryComparator implements Comparator<ProcessedHistory> {
        @Override
        public int compare(ProcessedHistory o1, ProcessedHistory o2) {
            return o1.getRaw().getDate()
                    .compareTo(o2.getRaw().getDate());
        }
    }
}
