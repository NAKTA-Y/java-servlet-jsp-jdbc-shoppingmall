package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;
import com.nhnacademy.shoppingmall.entity.product.Category;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.entity.product.ProductImage;
import com.nhnacademy.shoppingmall.repository.product.impl.ProductCategoriesRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.product.impl.ProductImageRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.product.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.service.product.ProductCategoriesService;
import com.nhnacademy.shoppingmall.service.product.ProductImageService;
import com.nhnacademy.shoppingmall.service.product.ProductService;
import com.nhnacademy.shoppingmall.service.product.impl.ProductCategoriesServiceImpl;
import com.nhnacademy.shoppingmall.service.product.impl.ProductImageServiceImpl;
import com.nhnacademy.shoppingmall.service.product.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequestMapping(value = "/admin/product_update.do", method = RequestMapping.Method.POST)
public class ProductUpdatePostController implements BaseController {
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String HOME_DIR = System.getProperty("user.home");
    private static final String PRODUCT_IMAGE_UPLOAD_DIR = HOME_DIR + File.separator + "resources/products";
    private static final String PRODUCT_THUMBNAIL_UPLOAD_DIR = HOME_DIR + File.separator + "resources/thumbnails";

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final ProductCategoriesService productCategoriesService = new ProductCategoriesServiceImpl(new ProductCategoriesRepositoryImpl());
    private final ProductImageService productImageService = new ProductImageServiceImpl(new ProductImageRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productIdParam = req.getParameter("product_id");
        String modelNumber = req.getParameter("model_number");
        String modelName = req.getParameter("model_name");
        String price = req.getParameter("price");
        String discountRate = req.getParameter("discount_rate");
        String stock = req.getParameter("stock");
        String brand = req.getParameter("brand");
        String description = req.getParameter("description");
        String[] categoryIDParams = req.getParameterValues("category_checkbox");

        if (ObjectUtils.anyNull(productIdParam, modelNumber, modelName, price, discountRate, stock, brand, description, categoryIDParams)) {
            resp.setStatus(400);
            throw new RuntimeException("BAD REQUEST");
        }
        int productId = Integer.valueOf(productIdParam);

        Product product = productService.getProduct(productId);
        String thumbnailPath = product.getThumbnail();

        try {
            for (Part part : req.getParts()) {
                String contentDispositon = part.getHeader(CONTENT_DISPOSITION);

                if (contentDispositon.contains("addedImages")) {
                    String fileName = UUID.randomUUID() + getFileExtension(contentDispositon);
                    String filePath = PRODUCT_IMAGE_UPLOAD_DIR + File.separator + productId + File.separator + fileName;

                    if (part.getSize() > 0) {
                        part.write(filePath);
                        part.delete();
                    }

                    ProductImage productImage = ProductImage.builder()
                            .productID(productId)
                            .productImage("/resources/products/" + productId + "/" + fileName)
                            .imageSize((int) part.getSize())
                            .build();

                    productImageService.saveProductImage(productImage);

                } else if (contentDispositon.contains("deletedImages")) {
                    // 파일 위치 가져오기
                    int productImageID = Integer.valueOf(readData(part));

                    ProductImage productImage = productImageService.getProductImageByID(productImageID);
                    if (!Objects.isNull(productImage)) {
                        productImageService.deleteByID(productImageID);

                        // 파일 삭제
                        File file = new File(System.getProperty("user.home") + productImage.getProductImage());
                        if (!file.delete()) log.error("File Delete Error!");
                    }

                } else if (contentDispositon.contains("thumbnail")) {
                    // 파일 위치 가져오기
                    String fileName = UUID.randomUUID() + getFileExtension(contentDispositon);
                    thumbnailPath = "/resources/thumbnails" + File.separator + fileName;

                    // 파일 삭제 및 추가 또는 교체
                    File file = new File(HOME_DIR + File.separator + product.getThumbnail());
                    if (!file.delete()) log.error("File Delete Error!");

                    if (part.getSize() > 0) {
                        part.write(PRODUCT_THUMBNAIL_UPLOAD_DIR + File.separator + fileName);
                        part.delete();
                    }
                }
            }
        } catch (IOException e) {
            log.error("파일 처리 중 문제 발생: " + e.getMessage());
            throw new WebApplicationException(500, "파일 업로드 중 문제가 발생 했습니다.");
        } catch (ServletException e) {
            log.error("Multipart 요청 처리 중 오류 발생", e);
            throw new WebApplicationException(400, "요청이 잘못 되었습니다.");
        }

        product.updateProductInfo(modelName, modelNumber, description, Integer.valueOf(stock), brand);
        product.updatePrice(Integer.valueOf(price), Integer.valueOf(discountRate));
        product.updateImage(product.getBanner(), thumbnailPath);

        productService.updateProductInfo(product);

        List<Category> categories = productCategoriesService.getCategoriesByProductID(productId);
        for (Category category : categories) {
            productCategoriesService.deleteProductCategory(productId, category.getCategoryID());
        }

        for (String categoryIDParam : categoryIDParams) {
            int categoryID = Integer.valueOf(categoryIDParam);
            productCategoriesService.saveProductCategory(productId, categoryID);
        }

        return "redirect:/admin/product_list.do";
    }

    private String getFileExtension(String contentDisposition) {
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.lastIndexOf("."), token.length()-1);
            }
        }

        return null;
    }

    private String readData(Part part) {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append(System.lineSeparator());
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return data.toString().trim();
    }

}
