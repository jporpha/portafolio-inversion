package com.jp.orpha.portafolio_inversion.services;

import org.springframework.web.multipart.MultipartFile;

public interface EtlExcelService {
    void processExcelFile(MultipartFile file);
}
