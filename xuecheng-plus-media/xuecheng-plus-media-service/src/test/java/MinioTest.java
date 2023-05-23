import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;

/**
 * @author cornelius
 * @date 2023/5/23 12:35
 * @description
 */
public class MinioTest {

    // Create a minioClient with the MinIO server playground, its access key and secret key.
    MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://192.168.101.65:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();
    /**
     * @description 测试上传文件
     */
    @Test
    public void upload() throws Exception {

        /**
         * description 通过扩展名来得到mimeType
         */
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch("mp4");
        String mimetype = MediaType.APPLICATION_OCTET_STREAM_VALUE;//通用mimeType 字节流
        if (extensionMatch != null){
            mimetype = extensionMatch.getMimeType();
        }

        /**上传文件需要minioclient调用uploadObject()方法实现文件的上传，
         * uploadObject方法需要参数UploadObjectArgs args
         */
        UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                .bucket("testbucket")//桶的名字
                .object("test001.mp4")//对象名 存放在根目录下。/test/test001.mp4则表示存放在test目录下
                .filename("/Users/caozide/Downloads/1073586.MP4")//指定本地的文件目录
               // .contentType("video/mp4")//设置媒体文件类型
                .contentType(mimetype)
                .build();
        minioClient.uploadObject(uploadObjectArgs);

    }

    /**
     * @description delete
     */
    @Test
    public void testDelete() throws Exception {

        //获得参数
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket("testbucket")
                .object("test001.mp4")
                .build();
        minioClient.removeObject(removeObjectArgs);


    }

    /**
     * 查询文件 也就是从mimio中下载文件
     */
    @Test
    public void testSelect() throws Exception {


        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket("testbucket")
                .object("test001.mp4")
                .build();

        //GetObjectResponse getObjectResponse = minioClient.getObject(getObjectArgs);
        //远程服务器上获取到的流对象 在进行md5校验时应当避免使用远程的流对象，不稳定
        FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
        //指定输出流
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/caozide/cc/1.mp4");
        IOUtils.copy(inputStream,fileOutputStream);

        /**
         * 检验文件的完整性，对文件的内容进行MD5加密
         */
        //String source_md5 = DigestUtils.md5Hex(inputStream);
        FileInputStream fileInputStream = new FileInputStream(new File("/Users/caozide/Downloads/1073586.MP4"));//本地的对象
        String source_md5 = DigestUtils.md5Hex(fileInputStream);
        FileInputStream fileInputStream1 = new FileInputStream(new File("/Users/caozide/cc/1.mp4"));//下载下来的流对象
        String local_md5 = DigestUtils.md5Hex(fileInputStream1);
        if (local_md5.equals(source_md5)){
            System.out.println("下载成功");
        }

    }
}
