package com.inn.cafe.cafe.restimpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafe.cafe.POJO.Bill;
import com.inn.cafe.cafe.constants.Cafeconstants;
import com.inn.cafe.cafe.rest.BillRest;
import com.inn.cafe.cafe.service.BillService;
import com.inn.cafe.cafe.utils.CafeUtils;

@RestController
public class BillRestImpl implements BillRest {

    @Autowired
    BillService billService;


    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        // TODO Auto-generated method stub
        try{
              return billService.generateReport(requestMap);
          
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<List<Bill>> getBills() {
       try{

        return billService.getBills();
       }catch(Exception ex){
        ex.printStackTrace();
       }

       return null;
    }


    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        // TODO Auto-generated method stub
        try{

            return billService.getPdf(requestMap);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return null;
    }


    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        // TODO Auto-generated method stub
        try{
            return billService.deleteBill(id);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
