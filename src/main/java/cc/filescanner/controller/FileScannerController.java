package cc.filescanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.filescanner.model.ScanRequest;
import cc.filescanner.model.ScanResponse;
import cc.filescanner.service.ScannerService;


@RestController("/scanner")
public class FileScannerController {

    @Autowired
    private ScannerService scanner;


    @RequestMapping("malware")
    public boolean scanForMalware(@RequestBody ScanRequest request) {
        return scanner.isCleanFile(request.getFileName());
    }


    @PostMapping("complete")
    public ScanResponse scan(@RequestBody ScanRequest request) {
        return scanner.scan(request);
    }
}
