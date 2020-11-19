package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({T001_VersionCheck.class, T002_ArchievedCheck.class, T003_HttpsRequestCheck.class,
    T004_DefaultUrlCheck.class, T005_BadUrlCountCheck.class, T006_GetUrlFromTelescopeCheck.class,
    T007_visitFileRecursiveCheck.class, T008_ArchiveUrlCheck.class,
    T009_availableUrlForMacCheck.class, T010_availableUrlForWindowCheck.class,
    T011_availableUrlWithJsonCheck.class})
public class AllTests {

}
