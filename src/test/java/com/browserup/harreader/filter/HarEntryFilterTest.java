package com.browserup.harreader.filter;

import com.browserup.harreader.model.HarEntry;
import com.browserup.harreader.model.HarRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class HarEntryFilterTest {
    private static final String PATTERN = "^(http|https)://abc(\\d?).com?";

    private static class TestData {
        private final String url;
        private final Boolean result;

        TestData(String url, Boolean result) {
            this.url = url;
            this.result = result;
        }
    }

    private static final TestData[] TEST_DATA = new TestData[] {
      new TestData("http://abc.com", true),
      new TestData("http://abc1.com", true),
      new TestData("https://abc.com", true),
      new TestData("ftp://abc.com", false),
      new TestData("http://abcd.com", false),
      new TestData("http:/abc.com", false),
    };

    private TestData data;

    public HarEntryFilterTest(TestData data) {
        this.data = data;
    }

    @Parameterized.Parameters
    public static Collection<TestData> data() {
        return Arrays.asList(TEST_DATA);
    }

    @Test
    public void testHarEntriesUrlPatternFilter() {
        HarEntriesFilter filter = new HarEntriesUrlPatternFilter(Pattern.compile(PATTERN));
        HarEntry harEntry = mock(HarEntry.class);
        HarRequest harRequest = mock(HarRequest.class);

        when(harEntry.getRequest()).thenReturn(harRequest);
        when(harRequest.getUrl()).thenReturn(data.url);

        Assert.assertEquals(data.result, filter.test(harEntry));
    }
}
