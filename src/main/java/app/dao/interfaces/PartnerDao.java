package app.dao.interfaces;

import app.dto.PartnerDto;
import java.util.List;

public interface PartnerDao {
    void createPartner(PartnerDto partner) throws Exception;
    PartnerDto findByRecordId(long recordId) throws Exception;
    List<PartnerDto> findAll() throws Exception;
    boolean existsByRecordId(long recordId) throws Exception;
    void updatePartner(PartnerDto partner) throws Exception;
    void deletePartner(long recordId) throws Exception;

    public PartnerDto findPartnerById(long id)throws Exception;

    public boolean existsByUserId(long userId)throws Exception;
}

