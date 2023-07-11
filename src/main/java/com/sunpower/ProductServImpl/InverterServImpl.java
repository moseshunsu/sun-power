package com.sunpower.ProductServImpl;

import com.sunpower.ProductDto.InverterRequest;
import com.sunpower.ProductRepo.InverterRepo;
import com.sunpower.ProductSevice.InverterServ;
import com.sunpower.Products.Inverter;
import com.sunpower.dto.Response;
import com.sunpower.utils.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InverterServImpl implements InverterServ {
    public final InverterRepo inverterRepo;

    public InverterServImpl(InverterRepo inverterRepo) {
        this.inverterRepo = inverterRepo;
}
    @Override
    public ResponseEntity<Response> registerInverter(InverterRequest inverterRequest) {

        boolean isInventorExist= inverterRepo.existsByName(inverterRequest.getName());

        if (isInventorExist) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .responseCode(ResponseUtils.PRODUCT_EXISTS_CODE)
                    .responseMessage(ResponseUtils.PRODUCT_EXISTS_MESSAGE)
                    .build());
        }

        Inverter inverter = Inverter.builder()
                .name(inverterRequest.getName())
                .batteryVoltageRange(inverterRequest.getBatteryVoltageRange())
                .componentEfficiency(inverterRequest.getComponentEfficiency())
                .productWarranty(inverterRequest.getProductWarranty())
                .amount(inverterRequest.getAmount())
                .build();

        Inverter savedInverter = inverterRepo.save(inverter);

        return ResponseEntity.ok(Response.builder()
                .responseCode(ResponseUtils.SUCCESS)
                .responseMessage(ResponseUtils.PRODUCT_SUCCESS_MESSAGE)
                .build());

    }

}
