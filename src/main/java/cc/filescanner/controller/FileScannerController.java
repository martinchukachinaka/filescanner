package cc.filescanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cc.filescanner.model.ScanRequest;
import cc.filescanner.model.ScanResponse;
import cc.filescanner.service.ScannerService;


@RestController
@RequestMapping("/scanner")
public class FileScannerController {

    @Autowired
    private ScannerService scanner;


    @GetMapping("checkfile")
    public boolean scanForMalware(@RequestBody ScanRequest request) {
        return scanner.isCleanFile(request.getFileName());
    }


    @PostMapping("checkfile")
    public ScanResponse scan(@RequestBody ScanRequest request) {
        return scanner.scan(request);
    }


    @PostMapping("valid")
    public ScanResponse scan(@RequestParam MultipartFile file, @RequestParam String ownerId) {
        ScanRequest request = new ScanRequest();
        request.setOwnerId(ownerId);
        request.setFile(file);
        return scanner.scan(request);
    }


    @GetMapping
    public String ping() {
        return "pong";
    }
}
