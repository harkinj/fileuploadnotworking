package hello;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * {@link Resource} implementation which supports piping the {@link InputStream}
 * of a {@link MultipartFile} directly to the resource consumer without having
 * to store the contents locally.
 *
 * @author AndyRokit
 *
 * @see org.springframework.web.multipart.MultipartFile
 * @see <a href="https://jira.spring.io/browse/SPR-16808">SPR-16808</a>
 */
public class MultipartFileResource implements Resource {
    private InputStream fileStream;

    public MultipartFileResource(InputStream fileStream) {
        this.fileStream = fileStream;
    }

    @Override
    public boolean exists() {
//        try {
//            InputStream is = getInputStream();
//            is.close();
//            return true;
//        }
//        catch (Exception isEx) {
//            return false;
//        }
        return true;
    }

    @Override
    public URL getURL() throws IOException {
        throw new FileNotFoundException(getDescription() + " cannot be resolved to URL");
    }

    @Override
    public boolean isOpen()
    {
        return true;
    }

    @Override
    public boolean isReadable()
    {
        return true;
    }


    @Override
    public URI getURI() throws IOException {
        throw new FileNotFoundException(getDescription() + " cannot be resolved to URI");
    }

    @Override
    public File getFile() throws IOException {
//        File convFile = new File(getFilename());
//        multipartFile.transferTo(convFile);
//        return convFile;
        throw new FileNotFoundException("getFile not implemented");

    }

    @Override
    public long contentLength() throws IOException {
//        return multipartFile.getSize();
        return -1;
//        throw new FileNotFoundException("contentLength not implemented");
    }

    @Override
    public long lastModified() throws IOException {
        return 0;
    }

    @Override
    public Resource createRelative(String relativePath) throws IOException {
        throw new FileNotFoundException("Cannot create a relative resource for " + getDescription());
    }

    @Override
    public String getFilename() {

        return "HardwiredFileName";
    }

    @Override
    public String getDescription() {

        return "HardwiredFileDesc";
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return fileStream;
    }
}
