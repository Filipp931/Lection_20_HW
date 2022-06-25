package com.sber.caching_downloader.bankomat;

import com.sber.caching_downloader.bankomat.server.exceptions.AccountIsLockedException;
import com.sber.caching_downloader.bankomat.server.exceptions.IncorrectSumException;
import com.sber.caching_downloader.bankomat.server.exceptions.NotEnoughMoneyException;
import com.sber.caching_downloader.bankomat.server.exceptions.PinIsNotValidatedException;
import com.sber.caching_downloader.bankomat.terminal.Terminal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
@RequestMapping("/terminal")
public class TerminalController {
    Terminal terminal;

    public TerminalController(Terminal terminal) {
        this.terminal = terminal;
    }

    @GetMapping("/")
    public String main(Model model) throws PinIsNotValidatedException {
        if(terminal.isPinIsValidated()) {
            model.addAttribute("balance", terminal.getBalance());
            return "terminal.html";
        } else return "pin.html";
    }

    @GetMapping("/pin")
    public String verifyPin(@RequestParam int pin, Model model){
        try {
            if (terminal.checkPassword(pin)) {
                return "redirect:";
            }
        } catch (AccountIsLockedException e) {
            model.addAttribute("locked", true);
            model.addAttribute("message", e.getMessage());
            return "pin.html";
        }
        model.addAttribute("incorrect", true);
        model.addAttribute("message", "Incorrect PIN!");
        return "pin.html";
    }

    @GetMapping("/withdraw")
    public String withdraw(@RequestParam int sum, Model model) throws PinIsNotValidatedException {
        try {
            terminal.withdraw(sum);
        } catch (IncorrectSumException|NotEnoughMoneyException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "redirect:";
    }


}
