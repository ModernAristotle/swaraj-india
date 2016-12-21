package com.aristotle.admin.controller.view;

import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.UploadedFile;
import com.aristotle.core.service.AwsFileManager;
import com.aristotle.core.service.ContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * Created by ravi on 21/12/16.
 */
@Controller
@Slf4j
public class CkEditorFileManagerController {

    @Autowired
    private AwsFileManager awsFileManager;

    @Autowired
    private ContentService contentService;

    @Value("${aws_access_key}")
    private String awsKey;
    @Value("${aws_access_secret}")
    private String awsSecret;
    @Value("${bucket_name:static.swarajindia.org}")
    private String bucketName;
    @Value("${static_data_env:dev}")
    private String staticDataEnv;

    @RequestMapping(value = "/images/browse")
    public ModelAndView browse(ModelAndView mv, HttpServletRequest request) {
        String ckEditor = request.getParameter("CKEditor");
        String funcNum = request.getParameter("CKEditorFuncNum");
        String langCode = request.getParameter("langCode");
        mv.getModel().put("ckEditor", ckEditor);
        mv.getModel().put("funcNum", funcNum);
        mv.getModel().put("langCode", langCode);
        List<UploadedFile> uploadedFiles = contentService.getUploadedFiles(0, 20);
        mv.getModel().put("uploadedFiles", uploadedFiles);
        mv.setViewName("ckeditor/browser");
        return mv;
    }

    @RequestMapping(value = "/images/upload")
    public ModelAndView upload(ModelAndView mv, HttpServletRequest request) {
        try {
            String ckEditor = request.getParameter("CKEditor");
            String funcNum = request.getParameter("CKEditorFuncNum");
            String langCode = request.getParameter("langCode");
            String contentId = request.getParameter("contentId");
            log.info("Parameters: " + request.getParameterMap());
            if (contentId == null) {
                throw new AppException("No Content Id provided");
            }
            String uploadSource = request.getParameter("uploadSource");
            mv.getModel().put("ckEditor", ckEditor);
            mv.getModel().put("funcNum", funcNum);
            mv.getModel().put("langCode", langCode);

            Collection<Part> parts = request.getParts();

            String fileUrl = null;
            for (Part onePart : parts) {
                if ("upload".equalsIgnoreCase(onePart.getName())) {
                    System.out.println(onePart.getContentType());
                    System.out.println(onePart.getName());
                    System.out.println(onePart.getSize());
                    System.out.println(onePart.getSubmittedFileName());
                    fileUrl = uploadImage(contentId, onePart.getSubmittedFileName(), onePart.getInputStream(), onePart.getSize(), uploadSource);
                }
            }

            mv.getModel().put("message", "Image Uploaded Succesfully");
            mv.getModel().put("fileUrl", fileUrl);
        } catch (Exception ex) {
            mv.getModel().put("message", "Unable to upload Image : " + ex.getMessage());
            log.error("Unable to upload Image ", ex);

        }

        mv.setViewName("ckeditor/upload");
        return mv;
    }

    private String uploadImage(String contentId, String fileName, InputStream fileToUpload, long size, String uploadSource) throws FileNotFoundException {
        String remoteFileName = staticDataEnv + "/content/" + contentId + "_" + fileName;
        remoteFileName = remoteFileName.toLowerCase();
        log.info("Uploading to S3");
        awsFileManager.uploadFileToS3(awsKey, awsSecret, bucketName, remoteFileName, fileToUpload);
        log.info("Upload Completed and saving to DB now");

        contentService.saveUploadedFile(remoteFileName, size, uploadSource);
        log.info("Saved in DB");

        return "https://s3-us-west-2.amazonaws.com/" + bucketName + "/" + remoteFileName;
    }

}
