package io.github.gitbucket.markedj;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class ExtendParseTest {
    
    public static void main(String[] args) throws IOException {
        ExtendParseTest test = new ExtendParseTest();
        test.debug();
    }
    
    private void debug() throws IOException {
        Options options = new Options();
        options.setBreaks(true);
        options.setLinkTargetBlank(true);
        options.setHeaderPrefix("markdown-agenda-");
        options.setHeaderIdSequential(true);

        String md = loadResourceAsString("debug.md");
        String result = Marked.marked(md, options);
        System.out.println(result);
    }
    
    @Test
    public void testHeaderId() throws Exception {
        Options options = new Options();
        options.setBreaks(true);
        options.setLinkTargetBlank(true);
        options.setHeaderPrefix("markdown-agenda-");
        options.setHeaderIdSequential(true);

        String md = loadResourceAsString("header_id.md");
        String result = Marked.marked(md, options);
        System.out.println(result);
        String expect = loadResourceAsString("header_id.html");
        System.out.println(expect);
        assertEquals(expect, result);
    }
    

    
    

    @Test
    public void testLinkTarget() throws Exception {
        Options options = new Options();
        options.setBreaks(true);
        options.setLinkTargetBlank(true);

        String md = loadResourceAsString("link_target.md");
        String result = Marked.marked(md, options);
//        System.out.println(result);
        String expect = loadResourceAsString("link_target.html");
//        System.out.println(expect);
        assertEquals(expect, result);
    }

    
    
    
    @Test
    public void testUNC() throws Exception {
        Options options = new Options();
        options.setBreaks(true);
        options.setLinkTargetBlank(true);
        String md = "[UNCPathLink](¥¥hoge¥data \"UNCPathLink\")";
        String result = Marked.marked(md, options);
        String expect = "<p><a href=\"¥¥hoge¥data\" title=\"UNCPathLink\">UNCPathLink</a></p>\n";
        assertEquals(expect, result);
    }
    
    @Test
    public void testFile() throws Exception {
        Options options = new Options();
        options.setBreaks(true);
        options.setLinkTargetBlank(true);
        String md = "[UNCPathLink](file://hoge/data \"UNCPathLink\")";
        String result = Marked.marked(md, options);
        String expect = "<p><a href=\"file://hoge/data\" title=\"UNCPathLink\">UNCPathLink</a></p>\n";
        assertEquals(expect, result);
    }
    
    
    @Test
    public void testSmb() throws Exception {
        Options options = new Options();
        options.setBreaks(true);
        options.setLinkTargetBlank(true);
        String md = "[UNCPathLink](smb://hoge/data \"UNCPathLink\")";
        String result = Marked.marked(md, options);
        String expect = "<p><a href=\"smb://hoge/data\" title=\"UNCPathLink\">UNCPathLink</a></p>\n";
        assertEquals(expect, result);
    }
    
    
    @Test
    public void testAmp() {
        String markdown = "```\n&read_data\n```";
        String result = Marked.marked(markdown);
        String check = "<pre><code>&amp;read_data\n</code></pre>\n";
        org.junit.Assert.assertEquals(check, result);
    }
    
    
    @Test
    public void testSlide() throws Exception {
        String md = Marked.marked("[slide 41]", new Options());
        String result = Marked.marked(md, new Options());
        assertEquals("<p><var class=\"slideshow\" slide=\"41\">41</var></p>\n", result);
    }
    
    
    private String loadResourceAsString(String path) throws IOException {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024 * 8];
            int length = 0;
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
            return new String(out.toByteArray(), "UTF-8");
        } finally {
            in.close();
        }
    }

}
