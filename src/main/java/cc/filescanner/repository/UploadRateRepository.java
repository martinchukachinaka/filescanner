package cc.filescanner.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cc.filescanner.redis.UploadRate;


@Repository
public interface UploadRateRepository extends CrudRepository<UploadRate, String> {
}
