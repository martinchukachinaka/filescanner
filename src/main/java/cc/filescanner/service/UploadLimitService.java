package cc.filescanner.service;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cc.filescanner.model.ScanRequest;
import cc.filescanner.redis.UploadRate;
import cc.filescanner.repository.UploadRateRepository;


@Service
public class UploadLimitService {

    private static final Logger LOG = LoggerFactory.getLogger(UploadLimitService.class);

    @Value("${scanner.uploads.max:25}")
    private Integer maxuploads;

    @Value("${scanner.uploads.period.milli:600000}")
    private Integer uploadperiod;

    @Autowired
    private UploadRateRepository uploadRateRepo;


    public boolean checkUploadLimit(ScanRequest request) {
        UploadRate rate = updateUploadRate(request);
        Long elapsedUploadTime = rate.getElapsedTime();
        if (elapsedUploadTime < uploadperiod && rate.getUploadCount() > maxuploads) {
            LOG.error("File upload limit exceeded.");
            return false;
        } else if (elapsedUploadTime > uploadperiod) {
            refreshUploadRate(rate);
        }
        return true;
    }


    private void refreshUploadRate(UploadRate rate) {
        rate.setUploadCount(1);
        rate.setCreationDate(rate.getLastModifiedDate());
        rate = uploadRateRepo.save(rate);
    }


    private UploadRate updateUploadRate(ScanRequest request) {
        UploadRate rate = null;
        Optional<UploadRate> rateOpt = uploadRateRepo.findById(request.getOwnerId());
        if (rateOpt.isPresent()) {
            rate = rateOpt.get();
        } else {
            rate = new UploadRate();
            rate.setCreationDate(new Date());
            rate.setId(request.getOwnerId());
        }
        rate.setLastModifiedDate(new Date());
        rate.setUploadCount(rate.getUploadCount() + 1);
        if (request.getFile() != null) {
            rate.setFileName(request.getFile().getName());
        }
        rate = uploadRateRepo.save(rate);
        LOG.info("rate: {}", rate);
        return rate;
    }
}
