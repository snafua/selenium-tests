package com.wikia.webdriver.testcases.adstests;

import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.core.annotations.NetworkTrafficDump;
import com.wikia.webdriver.common.core.url.Page;
import com.wikia.webdriver.common.dataprovider.ads.AdsDataProvider;
import com.wikia.webdriver.common.templates.mobile.MobileTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.componentobject.ad.VideoFanTakeover;
import com.wikia.webdriver.pageobjectsfactory.pageobject.adsbase.AdsBaseObject;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(
        groups = "AdsVuapMercury"
)
public class TestAdsVuapMercury extends MobileTestTemplate {

    private static final String URL_FIRSTQUARTILE = "ad_vast_point=firstquartile";
    private static final String URL_MIDPOINT = "ad_vast_point=midpoint";
    private static final int DELAY = 2;
    private static final int VIDEO_START_TIME = 0;

    @NetworkTrafficDump(useMITM = true)
    @Test(
            dataProviderClass = AdsDataProvider.class,
            dataProvider = "adsVuapMercury",
            groups = "AdsTopAdVideoClosesWhenFinishPlaysMercury"
    )
    public void adsTopAdVideoClosesWhenFinishPlaysMercury(Page page, String slotName, String iframeId, String videoUrl) {
        networkTrafficInterceptor.startIntercepting();
        AdsBaseObject ads = new AdsBaseObject(driver, urlBuilder.getUrlForPage(page));
        ads.scrollToSlot(slotName);
        ads.wait.forSuccessfulResponse(networkTrafficInterceptor, videoUrl);
        VideoFanTakeover videoFanTakeover = new VideoFanTakeover(driver, iframeId);

        videoFanTakeover.play();

        videoFanTakeover.waitForVideoPlayerVisible(slotName);
        videoFanTakeover.waitForVideoPlayerHidden(slotName);
    }

    @NetworkTrafficDump(useMITM = true)
    @Test(
            dataProviderClass = AdsDataProvider.class,
            dataProvider = "adsVuapMercury",
            groups = "AdsImageClickedOpensNewPageMercury"
    )
    public void adsImageClickedOpensNewPageMercury(Page page, String slotName, String iframeId, String videoUrl) {
        networkTrafficInterceptor.startIntercepting();
        AdsBaseObject ads = new AdsBaseObject(driver, urlBuilder.getUrlForPage(page));
        ads.scrollToSlot(slotName);
        ads.wait.forSuccessfulResponse(networkTrafficInterceptor, videoUrl);
        VideoFanTakeover videoFanTakeover = new VideoFanTakeover(driver, iframeId);

        videoFanTakeover.clickOnAdImage();

        String tabUrl = ads.switchToNewBrowserTab();
        videoFanTakeover.verifyFandomTabOpened(tabUrl);
    }

    @NetworkTrafficDump(useMITM = true)
    @Test(
            dataProviderClass = AdsDataProvider.class,
            dataProvider = "adsVuapMercury",
            groups = "AdsVuapVideoClosesWhenTapCloseButtonMercury"
    )
    public void adsVuapVideoClosesWhenTapCloseButtonMercury(Page page, String slotName, String iframeId, String videoUrl) {
        networkTrafficInterceptor.startIntercepting();
        AdsBaseObject ads = new AdsBaseObject(driver, urlBuilder.getUrlForPage(page));
        ads.scrollToSlot(slotName);
        ads.wait.forSuccessfulResponse(networkTrafficInterceptor, videoUrl);
        VideoFanTakeover videoFanTakeover = new VideoFanTakeover(driver, iframeId);

        videoFanTakeover.play();

        videoFanTakeover.waitForVideoPlayerVisible(slotName);

        videoFanTakeover.clickOnVideoCloseButon();

        videoFanTakeover.waitForVideoPlayerHidden(slotName);
    }

    @NetworkTrafficDump(useMITM = true)
    @Test(
            dataProviderClass = AdsDataProvider.class,
            dataProvider = "adsVuapMercury",
            groups = "AdsVuapCheckSlotSizesMercury"
    )
    public void adsVuapCheckSlotSizesMercury(Page page, String slotName, String iframeId, String videoUrl) {
        networkTrafficInterceptor.startIntercepting();
        AdsBaseObject ads = new AdsBaseObject(driver, urlBuilder.getUrlForPage(page));
        ads.scrollToSlot(slotName);
        ads.wait.forSuccessfulResponse(networkTrafficInterceptor, videoUrl);
        VideoFanTakeover videoFanTakeover = new VideoFanTakeover(driver, iframeId);

        videoFanTakeover.waitforAdToLoad();
        double imageHeight = videoFanTakeover.getAdSlotHigh(slotName);

        videoFanTakeover.play();

        videoFanTakeover.waitForVideoStart(slotName);
        double videoHeight = videoFanTakeover.getAdVideoHigh(slotName);

        videoFanTakeover.verifyVideoHasBigerSizeThenAdImage(videoHeight, imageHeight, slotName);

        videoFanTakeover.waitForVideoEnd(slotName);
        videoFanTakeover.verifyAdImageHasRequiredSize(imageHeight, slotName);
    }

    @NetworkTrafficDump(useMITM = true)
    @Test(
            dataProviderClass = AdsDataProvider.class,
            dataProvider = "adsVuapMercury",
            groups = "AdsVuapTimeProgressingMercury"
    )
    public void adsVuapTimeProgressingMercury(Page page, String slotName, String iframeId, String videoUrl) throws InterruptedException {
        networkTrafficInterceptor.startIntercepting();
        AdsBaseObject ads = new AdsBaseObject(driver, urlBuilder.getUrlForPage(page));
        ads.scrollToSlot(slotName);
        ads.wait.forSuccessfulResponse(networkTrafficInterceptor, videoUrl);
        VideoFanTakeover videoFanTakeover = new VideoFanTakeover(driver, iframeId);

        videoFanTakeover.play();

        videoFanTakeover.waitForVideoStart(slotName);
        ads.wait.forSuccessfulResponse(networkTrafficInterceptor, URL_FIRSTQUARTILE);
        double quartileTime = videoFanTakeover.getCurrentVideoTime(slotName).doubleValue();

        ads.wait.forSuccessfulResponse(networkTrafficInterceptor, URL_MIDPOINT);
        double midTime = videoFanTakeover.getCurrentVideoTime(slotName).doubleValue();

        Assertion.assertTrue(videoFanTakeover.isTimeProgressing(quartileTime, midTime));
    }

    @NetworkTrafficDump(useMITM = true)
    @Test(
            dataProviderClass = AdsDataProvider.class,
            dataProvider = "adsVuapMercury",
            groups = "AdsVuapVideoPauseMercury"
    )
    public void adsVuapVideoPauseMercury(Page page, String slotName, String iframeId, String videoUrl) throws InterruptedException {
        networkTrafficInterceptor.startIntercepting();
        AdsBaseObject ads = new AdsBaseObject(driver, urlBuilder.getUrlForPage(page));
        ads.scrollToSlot(slotName);
        ads.wait.forSuccessfulResponse(networkTrafficInterceptor, videoUrl);
        VideoFanTakeover videoFanTakeover = new VideoFanTakeover(driver, iframeId);

        videoFanTakeover.play();

        videoFanTakeover.waitForVideoStart(slotName);
        ads.wait.forSuccessfulResponse(networkTrafficInterceptor, URL_FIRSTQUARTILE);

        videoFanTakeover.pause();

        double time = videoFanTakeover.getCurrentVideoTime(slotName).doubleValue();

        Thread.sleep(DELAY * 1000);
        
        Assert.assertNotEquals(VIDEO_START_TIME, videoFanTakeover.getCurrentVideoTime(slotName).doubleValue());
        Assert.assertEquals(time, videoFanTakeover.getCurrentVideoTime(slotName).doubleValue());
    }
}
