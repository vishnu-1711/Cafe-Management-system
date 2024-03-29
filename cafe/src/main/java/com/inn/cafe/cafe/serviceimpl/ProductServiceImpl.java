package com.inn.cafe.cafe.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.cafe.cafe.JWT.JwtFilter;
import com.inn.cafe.cafe.POJO.Category;
import com.inn.cafe.cafe.POJO.Product;
import com.inn.cafe.cafe.constants.Cafeconstants;
import com.inn.cafe.cafe.dao.ProductDao;
import com.inn.cafe.cafe.service.ProductService;
import com.inn.cafe.cafe.utils.CafeUtils;
import com.inn.cafe.cafe.wrapper.ProductWrapper;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        // TODO Auto-generated method stub

        try {

            if (jwtFilter.isAdmin()) {
                if (validateProductMap(requestMap, false)) {

                    productDao.save(getProductFromMap(requestMap, false));
                    return CafeUtils.getResponseEntity("Product added successfully", HttpStatus.OK);
                }

                return CafeUtils.getResponseEntity(Cafeconstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else
                return CafeUtils.getResponseEntity(Cafeconstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();

        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        Product product = new Product();
        if (isAdd) {
            product.setId(Integer.parseInt(requestMap.get("id")));
        } else {
            product.setStatus("true");
        }

        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
        return product;

    }

    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        // TODO Auto-generated method stub
        try {

            return new ResponseEntity<>(productDao.getAllProduct(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        // TODO Auto-generated method stub
        try {

            if (jwtFilter.isAdmin()) {
                if (validateProductMap(requestMap, true)) {

                    Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));

                    if (!optional.isEmpty()) {

                        Product product = getProductFromMap(requestMap, true);
                        product.setStatus(optional.get().getStatus());
                        productDao.save(product);
                        return CafeUtils.getResponseEntity("Product Updated Successfully", HttpStatus.OK);
                    } else {
                        return CafeUtils.getResponseEntity("Product id doesnot exist", HttpStatus.OK);
                    }
                } else {
                    return CafeUtils.getResponseEntity(Cafeconstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }

            } else {
                return CafeUtils.getResponseEntity(Cafeconstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        // TODO Auto-generated method stub
        try {

            if (jwtFilter.isAdmin()) {

                Optional optional = productDao.findById(id);
                if (!optional.isEmpty()) {
                    productDao.deleteById(id);
                    return CafeUtils.getResponseEntity("Product deleted successfully", HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity("Product id doesnot exist", HttpStatus.OK);
            } else {
                return CafeUtils.getResponseEntity(Cafeconstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        // TODO Auto-generated method stub
        try {

            if (jwtFilter.isAdmin()) {

                Optional optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()) {

                    productDao.updateProductStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return CafeUtils.getResponseEntity("Product status updated successfulyy", HttpStatus.OK);

                }

                return CafeUtils.getResponseEntity("Product id doesnot exist", HttpStatus.OK);
            } else {
                return CafeUtils.getResponseEntity(Cafeconstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        // TODO Auto-generated method stub
       try{

        return new ResponseEntity<>(productDao.getProductByCategory(id),HttpStatus.OK);
       }catch(Exception ex){
        ex.printStackTrace();
       }
       return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
        // TODO Auto-generated method stub
        try{

            return new ResponseEntity<>(productDao.getProductById(id),HttpStatus.OK);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
