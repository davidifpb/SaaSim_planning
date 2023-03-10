package commons.config;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationRuntimeException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import planning.heuristic.AGHeuristic;
import provisioning.DynamicProvisioningSystem;

import commons.cloud.Contract;
import commons.cloud.MachineType;
import commons.cloud.Provider;
import commons.cloud.User;
import commons.io.Checkpointer;
import commons.sim.schedulingheuristics.RoundRobinHeuristic;
import commons.util.DataUnit;

public class ConfigurationTest {
	
	@Before
	public void setUp() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		Field field = Configuration.class.getDeclaredField("instance");
		field.setAccessible(true);
		field.set(null, null);
		Checkpointer.clear();
	}
	
	@After
	public void tearDown(){
		Checkpointer.clear();
	}
	
	@Test(expected=ConfigurationException.class)
	public void testBuildInstanceWithNullArgument() throws ConfigurationException {
		Configuration.buildInstance(null);
	}

	@Test(expected=ConfigurationException.class)
	public void testBuildInstanceWithEmptyArgument() throws ConfigurationException {
		Configuration.buildInstance("");
	}

	@Test(expected=ConfigurationException.class)
	public void testBuildInstanceWithInexistentConfigFile() throws ConfigurationException {
		Configuration.buildInstance(PropertiesTesting.INEXISTENT_CONFIG_FILE);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testBuildInstanceWithInexistentPlansFile() throws ConfigurationException {
		Configuration.buildInstance(PropertiesTesting.INEXISTENT_PLANS_FILE);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testBuildInstanceWithInexistentAppFile() throws ConfigurationException {
		Configuration.buildInstance(PropertiesTesting.INEXISTENT_APP_FILE);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testBuildInstanceWithInexistentUsersFile() throws ConfigurationException {
		Configuration.buildInstance(PropertiesTesting.INEXISTENT_USERS_FILE);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testBuildInstanceWithInexistentIaaSProvidersFile() throws ConfigurationException {
		Configuration.buildInstance(PropertiesTesting.INEXISTENT_IAAS_PROVIDERS_FILE);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testBuildInstanceWithInexistentIaaSPlanFile() throws ConfigurationException {
		Configuration.buildInstance(PropertiesTesting.INEXISTENT_IAAS_PLAN_FILE);
	}
	
	@Test(expected=ConfigurationRuntimeException.class)
	public void testGetInstanceWithoutBuild() {
		Configuration.getInstance();
	}
	
	@Test
	public void testBuildInstanceWithValidConfiguration() throws ConfigurationException {
		Configuration.buildInstance(PropertiesTesting.VALID_SINGLE_WORKLOAD_FILE);
		assertNotNull(Configuration.getInstance());
		
		assertTrue(Configuration.getInstance().isDebugMode());
		try{
			Configuration.getInstance().getIaaSOnDemandRisk();
			fail("IaaS on-demand risk was not set since workload was not read!");
		}catch(NullPointerException e){
		}
		assertEquals(0.72, Configuration.getInstance().getOptimalDPSPercentile(), 0.0);
	}
	
	@Test
	public void testBuildInstanceWithValidConfigurationWithoutDebugMode() throws ConfigurationException {
		Configuration.buildInstance(PropertiesTesting.VALID_SINGLE_WORKLOAD_FILE_1);
		assertNotNull(Configuration.getInstance());
		
		assertFalse(Configuration.getInstance().isDebugMode());
		try{
			Configuration.getInstance().getIaaSOnDemandRisk();
			fail("IaaS on-demand risk was not set since workload was not read!");
		}catch(NullPointerException e){
		}
		assertEquals(0.72, Configuration.getInstance().getOptimalDPSPercentile(), 0.0);
	}
	
	@Test
	public void testBuildInstanceWithValidConfigurationWithoutDPSPercentile() throws ConfigurationException {
		Configuration.buildInstance(PropertiesTesting.VALID_SINGLE_WORKLOAD_FILE_2);
		assertNotNull(Configuration.getInstance());
		
		assertFalse(Configuration.getInstance().isDebugMode());
		try{
			Configuration.getInstance().getIaaSOnDemandRisk();
			fail("IaaS on-demand risk was not set since workload was not read!");
		}catch(NullPointerException e){
		}
		assertEquals(0.95, Configuration.getInstance().getOptimalDPSPercentile(), 0.0);
	}
	
	@Test
	public void testBuildInstanceWithValidConfigurationAndPreviousSimData() throws ConfigurationException {
		Configuration.buildInstance(PropertiesTesting.VALID_SINGLE_WORKLOAD_FILE);
		Checkpointer.save();
		
		Configuration.buildInstance(PropertiesTesting.VALID_SINGLE_WORKLOAD_FILE);
		assertNotNull(Configuration.getInstance());
	}
	
//	private static void savePreviousData() {
//		Checkpointer.clear();
//		
//		Contract contract = new Contract("p1", 1, 55.55, 101.10, 86400000, 0.1, new long[]{0}, new double[]{0.0, 0.0}, 
//				10000, 0.2);
//		User user = new User(0, contract, 1000);
//		User user2 = new User(1, contract, 1000);
//		SimulationInfo info = new SimulationInfo(100, 4, 100);
//		
//		List<TypeProvider> types = new ArrayList<TypeProvider>();
//		types.add(new TypeProvider(0, MachineType.M1_SMALL, 0.1, 0.01, 100, 160, 10));
//		types.add(new TypeProvider(0, MachineType.C1_MEDIUM, 0.25, 0.1, 240, 360, 10));
//		
//		Provider provider = new Provider(0, "prov1", 10, 20, 0.15, new long[]{0}, new double[]{0.0, 0.0}, new long[]{1000}, 
//				new double[]{0.0, 0.1}, types);
//		Provider provider2 = new Provider(1, "prov2", 10, 20, 0.15, new long[]{0}, new double[]{0.0, 0.0}, new long[]{1000}, 
//				new double[]{0.0, 0.1}, types);
//		Provider provider3 = new Provider(2, "prov3", 10, 20, 0.15, new long[]{0}, new double[]{0.0, 0.0}, new long[]{1000}, 
//				new double[]{0.0, 0.1}, types);
//		
//		Checkpointer.loadScheduler();
//		Checkpointer.save(info, new User[]{user, user2}, new Provider[]{provider, provider2, provider3}, new LoadBalancer[]{});
//	}
	

	@Test(expected=ConfigurationException.class)
	public void testSaaSUsersWrongFile1() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_USERS_FILE_1);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testSaaSUsersWrongFile2() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_USERS_FILE_2);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testSaaSUsersWrongFile3() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_USERS_FILE_3);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testSaaSUsersWrongFile4() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_USERS_FILE_4);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testSaaSUsersWrongFile5() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_USERS_FILE_5);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testSaaSUsersWrongFile6() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_USERS_FILE_6);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testSaaSUsersWrongFile7() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_USERS_FILE_7);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testSaaSUsersWrongFile8() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_USERS_FILE_8);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testSaaSPlansWrongFile1() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_PLANS_FILE_1);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testSaaSPlansWrongFile2() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_PLANS_FILE_2);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testSaaSPlansWrongFile3() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_PLANS_FILE_3);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testSaaSPlansWrongFile4() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_PLANS_FILE_4);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testSaaSPlansWrongFile5() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_PLANS_FILE_5);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testIaaSProvidersWrongFile1() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_PROVIDERS_FILE_1);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testIaaSProvidersWrongFile2() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_PROVIDERS_FILE_2);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testIaaSProvidersWrongFile3() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_PROVIDERS_FILE_3);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testIaaSProvidersWrongFile7() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_PROVIDERS_FILE_7);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testIaaSProvidersWrongFile8() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_PROVIDERS_FILE_8);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testIaaSProvidersWrongFile9() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.WRONG_PROVIDERS_FILE_9);
	}
	
	@Test
	public void testEmptyIaaSPlanFile() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.EMPTY_IAAS_PLANS_FILE);
		
		Provider[] providers = Checkpointer.loadProviders();
		assertEquals(3, providers.length);
		for(Provider provider : providers){
			assertFalse(provider.canBuyMachine(true, MachineType.M1_SMALL));
			assertFalse(provider.canBuyMachine(true, MachineType.C1_MEDIUM));
			assertFalse(provider.canBuyMachine(true, MachineType.M1_LARGE));
			assertFalse(provider.canBuyMachine(true, MachineType.M1_XLARGE));
		}
	}
	
	@Test(expected=ConfigurationException.class)
	public void testInvalidIaaSPlanFile() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.INVALID_IAAS_PLANS_FILE);
	}
	
	@Test(expected=ConfigurationException.class)
	public void testInvalidIaaSPlanFile2() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.INVALID_IAAS_PLANS_FILE_2);
	}
	
	@Test
	public void testValidFile() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.VALID_SINGLE_WORKLOAD_FILE);
		Configuration config = Configuration.getInstance();
		assertEquals(RoundRobinHeuristic.class, config.getApplicationHeuristics()[0]);
		assertEquals(DynamicProvisioningSystem.class, config.getDPSHeuristicClass());
//		assertEquals(AGHeuristic.class, config.getPlanningHeuristicClass());
	}

	@Test
	public void testDifferentSaaSUsersAndPlansValidFile() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.VALID_DIFFERENT_USERS_FILE);
		Configuration config = Configuration.getInstance();
		User[] users = config.getUsers();
		assertNotNull(users);
		assertEquals(2, users.length);
		
		Contract c1 = users[0].getContract();
		assertNotNull(c1);
		assertEquals("diamond", c1.getName());
		assertEquals(100, c1.getPriority());
		assertEquals(299.95, c1.getPrice(), 0.0);
		assertEquals(0.0, c1.getSetupCost(), 0.0);
		assertEquals(10, c1.getCpuLimitInMillis(), 0.0);
		assertEquals(1, c1.getExtraCpuCostPerMillis(), 0.0);
		Assert.assertArrayEquals(new long[]{46080 * DataUnit.MB.getBytes()}, c1.getTransferenceLimitsInBytes());
		Assert.assertArrayEquals(new double[]{0,0.005/DataUnit.MB.getBytes()}, c1.getTransferenceCostsPerByte(), 0.0);
		assertEquals(3072 * DataUnit.MB.getBytes(), c1.getStorageLimitInBytes(), 0.0);
		assertEquals(0.1 / DataUnit.MB.getBytes(), c1.getExtraStorageCostPerByte(), 0.0);

		Contract c2 = users[1].getContract();
		assertNotNull(c2);
		assertEquals("platinum", c2.getName());
		assertEquals(50, c2.getPriority());
		assertEquals(149.95, c2.getPrice(), 0.0);
		assertEquals(0, c2.getSetupCost(), 0.0);
		assertEquals(10, c2.getCpuLimitInMillis(), 0.0);
		assertEquals(1, c2.getExtraCpuCostPerMillis(), 0.0);
		Assert.assertArrayEquals(new long[]{15360 * DataUnit.MB.getBytes()}, c2.getTransferenceLimitsInBytes());
		Assert.assertArrayEquals(new double[]{0,0.005 / DataUnit.MB.getBytes()}, c2.getTransferenceCostsPerByte(), 0.0);
		assertEquals(1024 * DataUnit.MB.getBytes(), c2.getStorageLimitInBytes(), 0.0);
		assertEquals(0.1 / DataUnit.MB.getBytes(), c2.getExtraStorageCostPerByte(), 0.0);
	}
	
	@Test
	public void testUniqueUserTypeSaaSUsersAndPlansValidFile() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.VALID_SINGLE_WORKLOAD_FILE);
		User[] users = Checkpointer.loadUsers();
		assertNotNull(users);
		assertEquals(1, users.length);
		
		Contract c1 = users[0].getContract();
		assertNotNull(c1);
		assertEquals("diamond", c1.getName());
		assertEquals(100, c1.getPriority());
		assertEquals(299.95, c1.getPrice(), 0.0);
		assertEquals(0.0, c1.getSetupCost(), 0.0);
		assertEquals(10, c1.getCpuLimitInMillis(), 0.0);
		assertEquals(1, c1.getExtraCpuCostPerMillis(), 0.0);
		Assert.assertArrayEquals(new long[]{46080 * DataUnit.MB.getBytes()}, c1.getTransferenceLimitsInBytes());
		Assert.assertArrayEquals(new double[]{0,0.005 / DataUnit.MB.getBytes()}, c1.getTransferenceCostsPerByte(), 0.0);
		assertEquals(3072 * DataUnit.MB.getBytes(), c1.getStorageLimitInBytes(), 0.0);
		assertEquals(0.1 / DataUnit.MB.getBytes(), c1.getExtraStorageCostPerByte(), 0.0);
	}
	
	/**
	 * This test verifies a valid file containing all attributes.
	 * @throws ConfigurationException 
	 */
	@Test
	public void testIaaSValidFile() throws ConfigurationException{
		Configuration.buildInstance(PropertiesTesting.VALID_SINGLE_WORKLOAD_FILE);
		Provider[] providers = Checkpointer.loadProviders();
		assertNotNull(providers);
		assertEquals(3, providers.length);

		long conversionFactor = 1024 * 1024 * 1024;
		
		Provider provider2 = providers[0];
		assertNotNull(provider2);
		assertEquals("rackspace", provider2.getName());
		assertEquals(20, provider2.getOnDemandLimit(), 0.0);
		assertEquals(100, provider2.getReservationLimit(), 0.0);
		assertEquals(0.15, provider2.getMonitoringCost(), 0.0);
		Assert.assertArrayEquals( new long[]{100 * conversionFactor}, provider2.getTransferInLimits());
		Assert.assertArrayEquals(new double[]{0.10/conversionFactor,0.09/conversionFactor}, provider2.getTransferInCosts(), 0.0);
		Assert.assertArrayEquals( new long[]{214748364800l}, provider2.getTransferOutLimits());
		Assert.assertArrayEquals(new double[]{0.10/conversionFactor,0.09/conversionFactor}, provider2.getTransferOutCosts(), 0.0);
		MachineType[] availableTypes2 = provider2.getAvailableTypes();
		Arrays.sort(availableTypes2);
		Assert.assertArrayEquals(new MachineType[]{MachineType.M1_SMALL, MachineType.M1_LARGE}, availableTypes2);
		assertEquals(0.085, provider2.getOnDemandCpuCost(MachineType.M1_SMALL), 0.0);
		assertEquals(0.03, provider2.getReservedCpuCost(MachineType.M1_SMALL), 0.0);
		assertEquals(1000, provider2.getReservationOneYearFee(MachineType.M1_SMALL), 0.0);
		assertEquals(2500, provider2.getReservationThreeYearsFee(MachineType.M1_SMALL), 0.0);
		assertEquals(0.12, provider2.getOnDemandCpuCost(MachineType.M1_LARGE), 0.0);
		assertEquals(0.13, provider2.getReservedCpuCost(MachineType.M1_LARGE), 0.0);
		assertEquals(1500, provider2.getReservationOneYearFee(MachineType.M1_LARGE), 0.0);
		assertEquals(3500, provider2.getReservationThreeYearsFee(MachineType.M1_LARGE), 0.0);

		Provider provider = providers[1];
		assertNotNull(provider);
		assertEquals("amazon", provider.getName());
		assertEquals(20, provider.getOnDemandLimit(), 0.0);
		assertEquals(100, provider.getReservationLimit(), 0.0);
		assertEquals(0.15, provider.getMonitoringCost(), 0.0);
		Assert.assertArrayEquals( new long[]{0}, provider.getTransferInLimits());
		Assert.assertArrayEquals(new double[]{0,0}, provider.getTransferInCosts(), 0.0);
		Assert.assertArrayEquals( new long[]{1 * conversionFactor, 10240 * conversionFactor, 
				51200 * conversionFactor, 153600 * conversionFactor}, provider.getTransferOutLimits());
		Assert.assertArrayEquals(new double[]{0 / conversionFactor, 0.12 / conversionFactor, 
				0.09 / conversionFactor, 0.07 / conversionFactor, 0.05 / conversionFactor}, provider.getTransferOutCosts(), 0.0);
		MachineType[] availableTypes = provider.getAvailableTypes();
		Arrays.sort(availableTypes);
		Assert.assertArrayEquals(new MachineType[]{MachineType.M1_SMALL,MachineType.M1_LARGE,MachineType.M1_XLARGE}, availableTypes);
		assertEquals(0.085, provider.getOnDemandCpuCost(MachineType.M1_SMALL), 0.0);
		assertEquals(0.03, provider.getReservedCpuCost(MachineType.M1_SMALL), 0.0);
		assertEquals(227.5, provider.getReservationOneYearFee(MachineType.M1_SMALL), 0.0);
		assertEquals(350, provider.getReservationThreeYearsFee(MachineType.M1_SMALL), 0.0);
		assertEquals(0.34, provider.getOnDemandCpuCost(MachineType.M1_LARGE), 0.0);
		assertEquals(0.12, provider.getReservedCpuCost(MachineType.M1_LARGE), 0.0);
		assertEquals(910, provider.getReservationOneYearFee(MachineType.M1_LARGE), 0.0);
		assertEquals(1400, provider.getReservationThreeYearsFee(MachineType.M1_LARGE), 0.0);
		assertEquals(0.68, provider.getOnDemandCpuCost(MachineType.M1_XLARGE), 0.0);
		assertEquals(0.24, provider.getReservedCpuCost(MachineType.M1_XLARGE), 0.0);
		assertEquals(1820, provider.getReservationOneYearFee(MachineType.M1_XLARGE), 0.0);
		assertEquals(2800, provider.getReservationThreeYearsFee(MachineType.M1_XLARGE), 0.0);

		Provider provider3 = providers[2];
		assertNotNull(provider3);
		assertEquals("gogrid", provider3.getName());
		assertEquals(5, provider3.getOnDemandLimit(), 0.0);
		assertEquals(10, provider3.getReservationLimit(), 0.0);
		assertEquals(0.1, provider3.getMonitoringCost(), 0.0);
		Assert.assertArrayEquals( new long[]{10 * conversionFactor}, provider3.getTransferInLimits());
		Assert.assertArrayEquals(new double[]{0.1 / conversionFactor, 0.0}, provider3.getTransferInCosts(), 0.0);
		Assert.assertArrayEquals( new long[]{20 * conversionFactor}, provider3.getTransferOutLimits());
		Assert.assertArrayEquals(new double[]{0.1 / conversionFactor, 0.0}, provider3.getTransferOutCosts(), 0.0);
		MachineType[] availableTypes3 = provider3.getAvailableTypes();
		Arrays.sort(availableTypes3);
		Assert.assertArrayEquals(new MachineType[]{MachineType.M1_LARGE,MachineType.C1_MEDIUM}, availableTypes3);
		assertEquals(0.085, provider3.getOnDemandCpuCost(MachineType.C1_MEDIUM), 0.0);
		assertEquals(0.03, provider3.getReservedCpuCost(MachineType.C1_MEDIUM), 0.0);
		assertEquals(1000, provider3.getReservationOneYearFee(MachineType.C1_MEDIUM), 0.0);
		assertEquals(2500, provider3.getReservationThreeYearsFee(MachineType.C1_MEDIUM), 0.0);
		assertEquals(0.12, provider3.getOnDemandCpuCost(MachineType.M1_LARGE), 0.0);
		assertEquals(0.13, provider3.getReservedCpuCost(MachineType.M1_LARGE), 0.0);
		assertEquals(1500, provider3.getReservationOneYearFee(MachineType.M1_LARGE), 0.0);
		assertEquals(3500, provider3.getReservationThreeYearsFee(MachineType.M1_LARGE), 0.0);
	}
}
