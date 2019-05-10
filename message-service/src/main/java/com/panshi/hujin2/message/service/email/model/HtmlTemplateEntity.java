package com.panshi.hujin2.message.service.email.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ycg 2018/11/30 15:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HtmlTemplateEntity {
    private String subject;
    private String templateHtml;
}
