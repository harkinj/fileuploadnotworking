package hello;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletRequest;

@Controller
public class FileUploadController {


    @PostMapping("/")
    public String upload(HttpServletRequest request) {

        System.out.println("Inside Upload -" + LocalDateTime.now() );

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            // Inform user about invalid request
            return "redirect:/";
        }

        //String filename = request.getParameter("name");

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload();

        // Parse the request
        try {
            FileItemIterator iter = upload.getItemIterator(request);
            while (iter.hasNext()) {
                FileItemStream item = iter.next();
                String name = item.getFieldName();
                InputStream stream = item.openStream();
                if (item.isFormField()) {
                    System.out.println("Form field " + name + " with value " + Streams.asString(stream) + " detected.");
                } else {


                    // try and write the file via stream
                    System.out.println("File field " + name + " with file name " + item.getName() + " detected." + "about to do a store"  + LocalDateTime.now());

                    stream.close();

                }
            }



        }catch (FileUploadException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:/";
    }

}
