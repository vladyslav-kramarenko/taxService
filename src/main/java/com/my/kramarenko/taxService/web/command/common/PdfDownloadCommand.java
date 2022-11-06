package com.my.kramarenko.taxService.web.command.common;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.my.kramarenko.taxService.exception.XmlException;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.command.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

//import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Login command.
 *
 * @author Vlad Kramarenko
 */
public class PdfDownloadCommand extends Command {

    @Serial
    private static final long serialVersionUID = -3071536594787692473L;
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.


    private static final Logger LOG = Logger.getLogger(PdfDownloadCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException {

        LOG.debug("Command starts");
        String htmlString = request.getParameter("htmlString");


        if (htmlString != null) {
            LOG.trace("html is here");
            try {
//                htmlString = "<HTML><BODY>" + htmlString + "</BODY></HTML>";
//                String SRC = "sample.html";

//                PrintWriter out = new PrintWriter(SRC);
//                out.println(htmlString);
//                out.close();
//                String DEST = "report.pdf";

//                createPdf(SRC, DEST);
//                generatePDF(SRC, DEST);
                setPdf(response, htmlString);
//                java.nio.file.Path path = Paths.get("test.pdf");
//                response.setContentLength((int) Files.size(path));
//              String fileName=getNewPdf(htmlString, response);
//                downloadFile(response, request, DEST);
//              printPdfToResponse(htmlString, response);
                return null;
            } catch (Exception e) {
                LOG.error(e.getMessage(),e);
                throw new ServletException("can't create a pdf", e);
            }
        } else {
            LOG.info("htmlString is null");
        }

        return Path.COMMAND_REPORT_LIST;
//
//        String reportTypeId = request.getParameter("reportTypeId");
//        ReportForm reportForm = ReportFormContainer.getForm(reportTypeId);
//        TaxForm taxForm = getTaxFormFromRequest(reportForm, request);
//        LOG.trace(taxForm);
//
////        String command = request.getParameter("command");
////        if (command.equals("downloadReport")) {
//        try {
//            String xml = download3(taxForm, reportForm);
////            response.setContentType("text/xml");
////            out.write(xml);
////            return null;
////            request.setAttribute("xml",xml);
////            return "/WEB-INF/jsp/emptyXml.jsp";
//            printXml(xml, response);
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition",
//                    "attachment;filename=report.xml");
//            return null;
//        } catch (XmlException | XMLStreamException e) {
//            LOG.error(e.getMessage());
//            throw new ServletException("Can't create download link", e);
//        }
//        }
//        return Path.COMMAND_REPORT_LIST;
    }

    public void setPdf(HttpServletResponse response, String html) throws XmlException {
        ConverterProperties converterProperties = new ConverterProperties();
//        HtmlConverter.convertToPdf(new FileInputStream(htmlSource),
//                new FileOutputStream(pdfDest), converterProperties);

        try (ServletOutputStream out = response.getOutputStream()) {
            HtmlConverter.convertToPdf(html, out,converterProperties);
//            HtmlConverter.convertToPdf(html, new PdfWriter(new File("test.pdf")),converterProperties);
            response.reset();
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment; filename=report.pdf");
//            response.setContentLength(html.getBytes(StandardCharsets.UTF_8).length);

//            int readBytes;
//            while ((readBytes = in.read()) != -1)
//                out.write(readBytes);
        } catch (IOException e) {
            LOG.error(e.getMessage(),e);
            throw new XmlException("can't create download link", e);
        }
    }

    public void createPdf(String src, String dest) throws IOException {
        LOG.trace("start");
        HtmlConverter.convertToPdf(new File(src), new File(dest));
        LOG.trace("finish");
    }

    public static void printPdfToResponse(String htmlFile, HttpServletResponse response) throws XmlException {
        try (ServletOutputStream out = response.getOutputStream();
             PdfWriter pdfWriter = new PdfWriter(out);
             PdfDocument pdfDocument = new PdfDocument(pdfWriter)) {

            response.reset();
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment; filename=report.pdf");
            response.setContentLength(htmlFile.getBytes(StandardCharsets.UTF_8).length);


            ConverterProperties converterProperties = new ConverterProperties();
            //For setting the PAGE SIZE
            pdfDocument.setDefaultPageSize(new PageSize(PageSize.A3));

            Document document = HtmlConverter.convertToDocument(htmlFile, pdfDocument, converterProperties);
            document.close();


//            int readBytes;
//            while ((readBytes = in.read()) != -1)
//                out.write(readBytes);
        } catch (IOException e) {
            LOG.error(e.getMessage(),e);
            throw new XmlException("can't create download link", e);
        }
    }

    public void generatePDF(String htmlFile, String pdfFile) throws ServletException {
        try {
            //Setting destination
            FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);

            PdfWriter pdfWriter = new PdfWriter(fileOutputStream);
            ConverterProperties converterProperties = new ConverterProperties();
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);

            //For setting the PAGE SIZE
            pdfDocument.setDefaultPageSize(new PageSize(PageSize.A4));

            Document document = HtmlConverter.convertToDocument(htmlFile, pdfDocument, converterProperties);
            document.close();
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            throw new ServletException("can't create download link", e);
        }
    }


    public static String getNewPdf(String htmlFile, HttpServletResponse response) throws XmlException {
        try {
//HTML String
            String name = "report.pdf";
            //Setting destination
            FileOutputStream fileOutputStream = new FileOutputStream(new File(name));

            PdfWriter pdfWriter = new PdfWriter(fileOutputStream);
            ConverterProperties converterProperties = new ConverterProperties();
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);

            //For setting the PAGE SIZE
            pdfDocument.setDefaultPageSize(new PageSize(PageSize.A3));

            Document document = HtmlConverter.convertToDocument(htmlFile, pdfDocument, converterProperties);
            document.close();

            return name;
        } catch (IOException e) {
            LOG.error(e.getMessage(),e);
            throw new XmlException("can't create download link", e);
        }
    }


    private void downloadFile(HttpServletResponse resp, HttpServletRequest req, String fileName) throws ServletException {
        LOG.trace("start");
        int ARBITARY_SIZE = 1048;
        resp.reset();
        resp.setContentType("application/x-download");
        resp.setHeader("Content-disposition", "attachment; filename=" + fileName);
        File initialFile = new File(fileName);
        try (InputStream in = new FileInputStream(initialFile);
             OutputStream out = resp.getOutputStream()) {
            int readBytes;
            while ((readBytes = in.read()) != -1)
                out.write(readBytes);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            throw new ServletException("can't load pdf", e);
        }
        LOG.trace("finish");
    }

    void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filePath = request.getParameter("filePath");
        String fileName = request.getParameter("fileName");

//        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
//        String mimeType = mimeTypesMap.getContentType(request.getParameter("fileName"));

//        response.setContentType(mimeType)
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