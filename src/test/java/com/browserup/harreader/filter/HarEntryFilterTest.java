package com.browserup.harreader.filter;

import com.browserup.harreader.model.HarEntry;
import com.browserup.harreader.model.HarRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class HarEntryFilterTest {

    private static class TestData {
        private final String url;
        private final Boolean result;
        private final String pattern;

        TestData(String url, Boolean result, String pattern) {
            this.url = url;
            this.result = result;
            this.pattern = pattern;
        }
    }

    private static final TestData[] TEST_DATA = new TestData[]{
        new TestData(
            "http://abc.com",
            true,
            "^(http|https)://abc(\\d?).com?"),
        new TestData(
            "http://abc1.com",
            true,
            "^(http|https)://abc(\\d?).com?"),
        new TestData(
            "https://abc.com?id=" + UUID.randomUUID(),
            true,
            "^https://abc\\.com\\?id=[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}?"),
        new TestData(
            "https://abc.com?id=" + UUID.randomUUID() + "invalid_uuid",
            false,
            "^https://abc\\.com\\?id=[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}?"),
        new TestData(
            "http://abc.com?someparam=someparam1",
            true,
            "^http://abc\\.com(\\z|\\?.*)"),
        new TestData(
            "http://abc.com",
            true,
            "^http://abc\\.com(\\z|\\?.*)"),
        new TestData(
            "http://abc.com;invalid",
            false,
            "^http://abc\\.com(\\z|\\?.*)"),
        new TestData(
            "ftp://abc.com",
            false,
            "^(http|https)://abc(\\d?).com?"),
        new TestData(
            "http://abcd.com",
            false,
            "^(http|https)://abc(\\d?).com?"),
        new TestData(
            "http:/abc.com",
            false,
            "^(http|https)://abc(\\d?).com?"),
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
        HarEntriesFilter filter = new HarEntriesUrlPatternFilter(Pattern.compile(data.pattern));
        HarEntry harEntry = mock(HarEntry.class);
        HarRequest harRequest = mock(HarRequest.class);

        when(harEntry.getRequest()).thenReturn(harRequest);
        when(harRequest.getUrl()).thenReturn(data.url);

        Assert.assertEquals(data.result, filter.test(harEntry));
    }
}
