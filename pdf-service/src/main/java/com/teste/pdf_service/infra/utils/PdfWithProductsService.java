package com.teste.pdf_service.infra.utils;

import com.teste.pdf_service.infra.kafka.OrderWithProductsMessageProducer;
import com.teste.pdf_service.infra.kafka.producer.PdfProducer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class PdfWithProductsService {

    private static final Logger log = LoggerFactory.getLogger(PdfWithProductsService.class);

    private final PdfProducer pdfProducer;

    public PdfWithProductsService(PdfProducer pdfProducer) {
        this.pdfProducer = pdfProducer;
    }

    public void generatePdf(OrderWithProductsMessageProducer orderMessageConsumer) {
        try (PDDocument document = new PDDocument()) {
            // Cria uma nova página no modo retrato (A4)
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Inicia um novo fluxo de conteúdo para a página
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Define a fonte e o tamanho do texto
                PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
                PDType1Font fontRegular = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

                // Adiciona bordas à página
//                drawBorders(contentStream, page);

                // Adiciona o cabeçalho e obtém a posição y final
                float titleEndY = addHeader(contentStream, page, font, "Order " + orderMessageConsumer.status());

                String dataTimeContent = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

                String textContent = String.format("Order status: %s - Date: %s",
                        orderMessageConsumer.status(), dataTimeContent);

                // Adiciona o conteúdo principal logo abaixo do título
                float contentStartY = titleEndY - 20; // 20 pontos de margem entre o título e o conteúdo
                float contentEndY = addContent(contentStream, fontRegular, String.format(textContent), contentStartY);

                // Colunas da tabela
                String[] columns = new String[]{"Produto", "Preço"};

                // Adiciona uma tabela abaixo do conteúdo
                String[][] data = orderMessageConsumer.products().stream()
                        .map(product -> new String[]{product.name(), String.valueOf(product.price())})
                        .toArray(String[][]::new);

                float tableStartY = contentEndY - 20; // 20 pontos de margem entre o conteúdo e a tabela
                addTable(document, contentStream, page, fontRegular, data, tableStartY);

                // Adiciona o rodapé na primeira página
                addFooter(contentStream, page, font, "Página 1");
            }

            // Verifica se o diretório "orders" existe; se não, cria o diretório
            File directory = new File("orders");
            if (!directory.exists()) {
                boolean dirCreated = directory.mkdirs(); // Cria o diretório e todos os diretórios pai, se necessário
                if (!dirCreated) {
                    throw new IOException("Não foi possível criar o diretório 'orders'.");
                }
            }

            // Salva o documento em um arquivo
            String nameOrderPdf = String.format("order-%s-%s.pdf", orderMessageConsumer.status(), orderMessageConsumer.id());
            document.save("orders/" + nameOrderPdf);
            pdfProducer.sendOrderPdfTopicConfirmedAndFail(orderMessageConsumer);
            log.info("Local do arquivo PDF: orders/{}", nameOrderPdf);
            log.info("PDF criado com sucesso!");
        } catch (IOException e) {
            log.error("Erro ao gerar o PDF", e);
            throw new RuntimeException("Erro ao gerar o PDF", e);
        }
    }

    // Método para adicionar bordas à página
    private void drawBorders(PDPageContentStream contentStream, PDPage page) throws IOException {
        float margin = 50; // Margem de 50 pontos
        float pageWidth = page.getMediaBox().getWidth();
        float pageHeight = page.getMediaBox().getHeight();

        contentStream.setLineWidth(1); // Define a espessura da linha
        contentStream.addRect(margin, margin, pageWidth - 2 * margin, pageHeight - 2 * margin); // Desenha um retângulo
        contentStream.stroke(); // Aplica o traço
    }

    // Método para adicionar o cabeçalho e retornar a posição y final
    private float addHeader(PDPageContentStream contentStream, PDPage page, PDType1Font font, String title) throws IOException {
        int fontSize = 18;
        float margin = 50; // Margem de 50 pontos
        float pageWidth = page.getMediaBox().getWidth();

        // Calcula a posição x para centralizar o título
        float titleWidth = font.getStringWidth(title) / 1000 * fontSize;
        float startX = (pageWidth - titleWidth) / 2;

        // Define a fonte e o tamanho do texto
        contentStream.setFont(font, fontSize);

        // Adiciona o título centralizado
        contentStream.beginText();
        contentStream.newLineAtOffset(startX, page.getMediaBox().getHeight() - margin - fontSize); // Posição y abaixo da margem superior
        contentStream.showText(title);
        contentStream.endText();

        // Retorna a posição y final após o título
        return page.getMediaBox().getHeight() - margin - fontSize - 10; // 10 pontos de margem abaixo do título
    }

    // Método para adicionar o conteúdo principal e retornar a posição y final
    private float addContent(PDPageContentStream contentStream, PDType1Font font, String content, float startY) throws IOException {
        int fontSize = 12;
        float margin = 50; // Margem esquerda e direita
        float pageWidth = PDRectangle.A4.getWidth() - 2 * margin; // Largura disponível para o texto
        float lineHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;

        // Define a fonte e o tamanho do texto
        contentStream.setFont(font, fontSize);

        // Divide o texto em palavras
        String[] words = content.split(" ");
        StringBuilder line = new StringBuilder();

        // Itera sobre as palavras
        for (String word : words) {
            // Adiciona a palavra à linha atual
            String newLine = line.toString().isEmpty() ? word : line + " " + word;

            // Calcula a largura da nova linha
            float lineWidth = font.getStringWidth(newLine) / 1000 * fontSize;

            // Se a nova linha couber na largura disponível, adiciona a palavra à linha
            if (lineWidth <= pageWidth) {
                line.append(line.toString().isEmpty() ? word : " " + word);
            } else {
                // Se não couber, escreve a linha atual e começa uma nova linha
                writeLine(contentStream, line.toString(), margin, startY);
                startY -= lineHeight; // Move para a próxima linha
                line = new StringBuilder(word); // Começa uma nova linha com a palavra atual
            }
        }

        // Escreve a última linha, se houver
        if (!line.toString().isEmpty()) {
            writeLine(contentStream, line.toString(), margin, startY);
        }

        // Retorna a posição y final após o texto
        return startY - lineHeight; // Subtrai a altura da última linha
    }

    // Método para escrever uma linha de texto
    private void writeLine(PDPageContentStream contentStream, String text, float x, float y) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }

    // Método para adicionar uma tabela com suporte a múltiplas páginas
    private void addTable(PDDocument document, PDPageContentStream contentStream, PDPage page, PDType1Font font, String[][] data, float startY) throws IOException {
        float margin = 50; // Margem de 50 pontos
        float pageWidth = page.getMediaBox().getWidth();
        float tableWidth = pageWidth - 2 * margin;
        float cellHeight = 20;
        float cellMargin = 5;
        float footerHeight = 30; // Altura do rodapé

        // Calcula a largura de cada coluna
        float[] columnWidths = new float[data[0].length];
        for (int i = 0; i < columnWidths.length; i++) {
            columnWidths[i] = tableWidth / data[0].length; // Largura igual para todas as colunas
        }

        // Adiciona as linhas da tabela
        for (int i = 0; i < data.length; i++) {
            // Verifica se a tabela ultrapassa o espaço disponível na página
            if (startY - (i * cellHeight) < margin + footerHeight) {
                // Fecha o conteúdo da página atual
                contentStream.close();

                // Cria uma nova página
                PDPage newPage = new PDPage(PDRectangle.A4);
                document.addPage(newPage);

                // Inicia um novo fluxo de conteúdo para a nova página
                contentStream = new PDPageContentStream(document, newPage);

                // Redefine a fonte e o tamanho da fonte
                contentStream.setFont(font, 12); // Defina a fonte e o tamanho da fonte

                // Adiciona bordas na nova página
//                drawBorders(contentStream, newPage);

                // Redefine a posição y inicial para a nova página
                startY = newPage.getMediaBox().getHeight() - margin; // Começa no topo da nova página

                // Adiciona o número da página no rodapé
                addFooter(contentStream, newPage, font, "Página " + (document.getNumberOfPages()));
            }

            for (int j = 0; j < data[i].length; j++) {
                float x = margin + j * columnWidths[j];
                float y = startY - i * cellHeight;

                // Desenha a borda da célula
                contentStream.setLineWidth(0.5f); // Espessura da borda
                contentStream.addRect(x, y - cellHeight, columnWidths[j], cellHeight); // Retângulo da célula
                contentStream.stroke(); // Aplica o traço

                // Adiciona o conteúdo da célula
                contentStream.beginText();
                contentStream.newLineAtOffset(x + cellMargin, y - cellHeight + cellMargin);
                contentStream.showText(data[i][j]);
                contentStream.endText();
            }
        }

        // Fecha o fluxo de conteúdo da última página
        contentStream.close();
    }

    // Método para adicionar o rodapé
    private void addFooter(PDPageContentStream contentStream, PDPage page, PDType1Font font, String footerText) throws IOException {
        float pageWidth = page.getMediaBox().getWidth();
        int fontSize = 10;

        // Calcula a posição x para centralizar o rodapé
        float footerWidth = font.getStringWidth(footerText) / 1000 * fontSize;
        float startX = (pageWidth - footerWidth) / 2;

        // Define a fonte e o tamanho do texto
        contentStream.setFont(font, fontSize);

        // Adiciona o rodapé
        contentStream.beginText();
        contentStream.newLineAtOffset(startX, 30); // Posição y acima da margem inferior
        contentStream.showText(footerText);
        contentStream.endText();
    }
}