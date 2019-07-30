package cc.filescanner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cc.filescanner.model.ScanRequest;
import cc.filescanner.service.ScannerService;


@RestController("/scanner")
public class FileScannerController {

    @Autowired
    private ScannerService scanner;

    private static final Logger LOG = LoggerFactory.getLogger(FileScannerController.class);


    @RequestMapping
    public boolean scanForMalware(@RequestBody ScanRequest request) {
        LOG.info("gets in here...: {}", request);
        return scanner.isCleanFile(request.getFileName());
    }


    @PostMapping
    public boolean scanForMalware(@RequestParam MultipartFile file) {
        return scanner.isCleanFile(file);
    }
}
