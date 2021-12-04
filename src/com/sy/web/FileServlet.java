package com.sy.web;

import com.alibaba.fastjson.JSON;
import com.sy.entity.Job;
import com.sy.service.FileService;
import com.sy.service.Impl.FileServiceImpl;
import com.sy.util.RequestContextUtil;
import com.sy.util.RequestMapping;
import com.sy.util.Result;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@RequestMapping("/file")
public class FileServlet {
    private FileService service = new FileServiceImpl();
    private HttpServletRequest request = RequestContextUtil.getRequest();
    private HttpServletResponse response = RequestContextUtil.getResponse();

    @RequestMapping("/add.do")
    public void fileAdd() throws Exception {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
//        request.setCharacterEncoding("utf-8");
        factory.setSizeThreshold(1024 * 1024 * 10);
        /*File uploadTemp = new File("e:\\uploadTemp");
        uploadTemp.mkdirs();
        factory.setRepository(uploadTemp);*/

        //设置单个文件大小限制
        upload.setFileSizeMax(1024L * 1024 * 1024);
        //设置所有文件总和大小限制
        upload.setSizeMax(1024L * 1024 * 1024 * 3);

        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            com.sy.entity.File file = new com.sy.entity.File();
            for (FileItem fileItem : fileItems) {
//                System.out.println("fieldName:" + fileItem.getFieldName());
//                System.out.println("name:" + fileItem.getName());
//                System.out.println("string:" + fileItem.getString("UTF-8"));
//                System.out.println("contentType:" + fileItem.getContentType());
//                System.out.println("size:" + fileItem.getSize() + "byte");
//                System.out.println("isFieldForm:" + fileItem.isFormField());
//                System.out.println("inputStream:" + org.apache.commons.io.IOUtils.toString(fileItem.getInputStream()));
//                System.out.println("*************");
                String suffix = "";
                String uuid = "";
                if (!fileItem.isFormField() && fileItem.getName() != null && !"".equals(fileItem.getName())) {
                    String filName = fileItem.getName();
                    //利用UUID生成伪随机字符串，作为文件名避免重复
                    uuid = UUID.randomUUID().toString();
                    System.out.println(uuid);
                    //获取文件后缀名
                    suffix = filName.substring(filName.lastIndexOf("."));
                    file.setFileName(uuid + suffix);
                    //获取文件上传目录路径，在项目部署路径下的upload目录里。若想让浏览器不能直接访问到图片，可以放在WEB-INF下
                    String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
                    String path = "C:\\Users\\Administrator\\IdeaProjects\\PMSWeb\\web\\upload";
//                    System.out.println();
                    File files = new File(path);
                    files.mkdirs();
                    //写入文件到磁盘，该行执行完毕后，若有该临时文件，将会自动删除
                    /*byte[] bytes = fileItem.get();
                    OutputStream outputStream = new FileOutputStream(new File(path, uuid + suffix));
                    outputStream.write(bytes);
                    outputStream.close();*/

                    /*FileOutputStream fout = null;

                    try {
                        fout = new FileOutputStream(new File(path, uuid + suffix));
                        fout.write(fileItem.get());
                        fout.close();
                    } finally {
                        IOUtils.closeQuietly(fout);
                    }*/
                    fileItem.write(new File(uploadPath, uuid + suffix));
                    fileItem.write(new File(path, uuid + suffix));

                } else {
                    if ("title".equals(fileItem.getFieldName())) {
                        String title = fileItem.getString("UTF-8");
                        file.setTitle(title);
//                        System.out.println(title);
                    } else if ("remark".equals(fileItem.getFieldName())) {
                        String remark = fileItem.getString("UTF-8");
                        file.setRemark(remark);
//                        System.out.println(remark);
                    } else if ("uid".equals(fileItem.getFieldName())) {
                        String uid = fileItem.getString("UTF-8");
                        if (uid != null) {
                            file.setUid(Integer.parseInt(uid));
                        }
                    }

                }
            }
            Result result = service.addFile(file);
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(result));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/list.do")
    public void fileList() throws Exception {
        String title = request.getParameter("title");
        String pageNow = request.getParameter("page");
        String pageSize = request.getParameter("limit");
        com.sy.entity.File file = new com.sy.entity.File();
        file.setTitle(title);
        Result result = service.findFile(file, pageNow, pageSize);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }

    @RequestMapping("/removes.do")
    public void removeFiles() throws Exception {
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String[] parameter = request.getParameterValues("newsId[]");
        String[] fileNames = request.getParameterValues("newName[]");
        Result result = service.removeFile(parameter, fileNames);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }

    @RequestMapping("/remove.do")
    public void removeJob() throws Exception {
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String parameter = request.getParameter("newsId");
        String fileName = request.getParameter("newName");
        Result result = service.removeFile(parameter, fileName);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }
}
