package com.my.kramarenko.taxService.web.command.common;

import com.my.kramarenko.taxService.exception.XmlException;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.xml.WriteXmlStAXController;
import com.my.kramarenko.taxService.xml.entity.TaxForm;
import com.my.kramarenko.taxService.xml.forms.ReportForm;
import com.my.kramarenko.taxService.xml.forms.ReportFormContainer;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import javax.activation.MimetypesFileTypeMap;
import javax.xml.stream.XMLStreamException;
import java.io.*;

import static com.my.kramarenko.taxService.web.command.util.ReportUtil.getTaxFormFromRequest;

/**
 * Login command.
 *
 * @author Vlad Kramarenko
 */
public class DownloadXmlReportCommand extends Command {

    @Serial
    private static final long serialVersionUID = -3071536594787692473L;
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.


    private static final Logger LOG = Logger.getLogger(DownloadXmlReportCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException {

        LOG.debug("Command starts");

        String reportTypeId = request.getParameter("reportTypeId");
        ReportForm reportForm = ReportFormContainer.getForm(reportTypeId);
        TaxForm taxForm = getTaxFormFromRequest(reportForm, request);
        LOG.trace(taxForm);

//        String command = request.getParameter("command");
//        if (command.equals("downloadReport")) {
        try {
            String xml = download3(taxForm, reportForm);
//            response.setContentType("text/xml");
//            out.write(xml);
//            return null;
//            request.setAttribute("xml",xml);
//            return "/WEB-INF/jsp/emptyXml.jsp";
            printXml(xml, response);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment;filename=report.xml");
            return null;
        } catch (XmlException | XMLStreamException e) {
            LOG.error(e.getMessage());
            throw new ServletException("Can't create download link", e);
        }
//        }
//        return Path.COMMAND_REPORT_LIST;
    }

    private static void printXml(String xml, HttpServletResponse response) throws XmlException {
        LOG.trace(xml);
        try {
            ServletOutputStream out = response.getOutputStream();
//            StringBuffer sb = generateCsvFileBuffer();
//
            InputStream in =
                    new ByteArrayInputStream(xml.getBytes("UTF-8"));
//            Charset charset = Charset.forName("UTF-8");
//
//            byte[] outputByte = xml.getBytes(charset);
            byte[] outputByte = new byte[4096];
            //copy binary contect to output stream
            while (in.read(outputByte, 0, 4096) != -1) {
                out.write(outputByte, 0, 4096);
            }
            in.close();
            out.flush();
            out.close();

        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new XmlException("can't create download link", e);
        }
//        return null;
    }

    private static String download3(TaxForm taxForm, ReportForm reportForm) throws XmlException, XMLStreamException {
        WriteXmlStAXController stAXController = new WriteXmlStAXController();
        String result;
        result = stAXController.writeToString(taxForm, reportForm);
        return result;
    }

    void download1(HttpServletRequest request, HttpServletResponse response, TaxForm taxForm, ReportForm reportForm) throws IOException, XmlException {
//        String fileId = request.getParameter("id");
//
//        // Check if ID is supplied to the request.
//        if (fileId == null) {
//            // Do your thing if the ID is not supplied to the request.
//            // Throw an exception, or send 404, or show default/warning page, or just ignore it.
//            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
//            return;
//        }

        // Lookup File by FileId in database.
        // Do your "SELECT * FROM File WHERE FileID" thing.
//        File file = fileDAO.find(fileId);

        // Check if file is actually retrieved from database.
//        if (file == null) {
//            // Do your thing if the file does not exist in database.
//            // Throw an exception, or send 404, or show default/warning page, or just ignore it.
//            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
//            return;
//        }

        // Init servlet response.
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
//        response.addHeader("Content-Type", "text/xml");
        response.setContentType("text/xml");
//        response.setContentType(file.getContentType());
//        response.setHeader("Content-Length", String.valueOf(file.getLength()));
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + "file.xml" + "\"");

        // Prepare streams.
//        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open streams.
//            input = new BufferedInputStream(file.getContent(), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
            WriteXmlStAXController write = new WriteXmlStAXController();
            write.writeToFileOutputStream(output, taxForm, reportForm);
            output.flush();


//            StreamReader reader = new StreamReader(stream);
//            string text = reader.ReadToEnd();

            // Write file contents to response.
//            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
//            int length;
//            while ((length = input.read(buffer)) > 0) {
//                output.write(buffer, 0, length);
//            }
        } catch (XMLStreamException e) {
            LOG.error(e.getMessage());
            throw new XmlException("can't create download link", e);
        } finally {
            // Gently close streams.
            close(output);
//            close(input);
        }

//        try {
//            // Open streams.
//            input = new BufferedInputStream(file.getContent(), DEFAULT_BUFFER_SIZE);
//            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
//
//            // Write file contents to response.
//            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
//            int length;
//            while ((length = input.read(buffer)) > 0) {
//                output.write(buffer, 0, length);
//            }
//        } finally {
//            // Gently close streams.
//            close(output);
//            close(input);
//        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
            }
        }
    }

    void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filePath = request.getParameter("filePath");
        String fileName = request.getParameter("fileName");

        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        String mimeType = mimeTypesMap.getContentType(request.getParameter("fileName"));

        response.setContentType(mimeType);
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);

        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(filePath);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
    }

}