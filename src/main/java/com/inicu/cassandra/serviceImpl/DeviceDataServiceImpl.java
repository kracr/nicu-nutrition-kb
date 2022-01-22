package com.inicu.cassandra.serviceImpl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.inicu.models.*;
import com.inicu.postgres.dao.SettingDao;
import com.inicu.postgres.entities.*;
import com.inicu.postgres.serviceImpl.SettingsServiceImp;
import com.inicu.postgres.utility.*;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import kafka.utils.Json;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.inicu.analytics.entities.ClassifierWeights;
import com.inicu.analytics.entities.FittingResult;
import com.inicu.cassandra.dao.DeviceDAO;
import com.inicu.cassandra.entities.CameraImageStream;
import com.inicu.cassandra.entities.DeviceDataCarescapeACK;
import com.inicu.cassandra.entities.DeviceDataInfinity;
import com.inicu.cassandra.entities.PatientDeviceDataInicu;
import com.inicu.cassandra.entities.PatientMonitorData;
import com.inicu.cassandra.entities.PatientVentilatorData;
import com.inicu.cassandra.models.AddDeviceDropDowns;
import com.inicu.cassandra.models.AddDeviceListJson;
import com.inicu.cassandra.models.AddDeviceMasterJson;
import com.inicu.cassandra.models.AddDeviceTemplateJson;
import com.inicu.cassandra.models.MonitorDummyDataJson;
import com.inicu.cassandra.models.MonitorJSON;
import com.inicu.cassandra.models.NewRegisteredDevicesJSON;
import com.inicu.cassandra.service.DeviceDataService;
import com.inicu.cassandra.utility.InicuCassandraTemplate;
import com.inicu.cassandra.utility.MappingConstants;
import com.inicu.device.geb450monitor.carescapeACK.SaveCarescapeACkDataPostgres;
import com.inicu.device.utility.DeviceConstants;
import com.inicu.device.utility.DeviceObjectMapper;
import com.inicu.device.utility.RegistrationConstants;
import com.inicu.deviceadapter.library.push.KafkaProducerDataModel;
import com.inicu.deviceadapter.library.push.PushData;
import com.inicu.hl7.data.acquisition.HL7Constants;
import com.inicu.kafkacassandra.connector.constants.Constants;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.service.PatientDeviceService;
import com.inicu.postgres.service.UserPanelService;
import weka.associations.LabeledItemSet;

import static java.lang.Float.parseFloat;

/**
 * DeviceDataServiceImpl class used for CRUD operations.
 *
 * @author Dipin
 *
 */

@Service
public class DeviceDataServiceImpl implements DeviceDataService {

	PushData pushData = new PushData(new KafkaProducerDataModel(BasicConstants.KAFKA_HOSTNAME,
			BasicConstants.KAFKA_PORT, BasicConstants.KAFKA_TOPIC));
	ObjectMapper mapper = new ObjectMapper();
	HashMap<String, String> mapDataValues;

	@Autowired
	private DeviceDAO deviceDAO;

	@Autowired
	public InicuCassandraTemplate cassandraTemplate;

	@Autowired
	private UserPanelService userPanelService;

	@Autowired
	private PatientDeviceService patientDeviceService;

	@Autowired
	PatientDao patientDao;

	@Autowired
	InicuDao inicuDao;

	ExecutorService executor = Executors.newFixedThreadPool(1);
	ExecutorService maxExecutor = Executors.newFixedThreadPool(1);
	ExecutorService patientDataExecutor = Executors.newFixedThreadPool(1);
	ExecutorService inifinityPool = Executors.newFixedThreadPool(1);
	ExecutorService carescapeackPool = Executors.newFixedThreadPool(1);

	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// private Configuration runConf;
	//
	// private IceApplication app;

	private final static File[] searchPath = new File[]{new File(".JumpStartSettings"),
			new File(System.getProperty("user.home"), ".JumpStartSettings")};

	/**
	 * Default Constructor
	 */
	public DeviceDataServiceImpl() {
		super();
	}

	@Override
	public void startService(List<String> listOfId) {
		// startMonitorService(listOfId);
	}

	// private void startMonitorService(List<String> listOfId) {
	//
	// String domainId = listOfId.get(0).toString();
	// if (domainId != null && !domainId.isEmpty()) {
	// String[] array = new String[domainId.length()];
	// for (int i = 0; i < domainId.length(); i++) {
	// array[i] = String.valueOf(domainId.charAt(i));
	// }
	// try {
	// int domain = Integer.parseInt(domainId);
	// // Here we use 'default' Quality of Service settings supplied by
	// // x73-idl-rti-dds
	// IceQos.loadAndSetIceQos();
	//
	// // A domain participant is the main access point into the DDS
	// // domain. Endpoints are created within the domain participant
	// DomainParticipant participant =
	// DomainParticipantFactory.get_instance().create_participant(domain,
	// DomainParticipantFactory.PARTICIPANT_QOS_DEFAULT, null,
	// StatusKind.STATUS_MASK_NONE);
	// // OpenICE divides the global DDS data-space into individual
	// // patient contexts using the DDS partitioning mechanism.
	// // Partitions are a list of strings (and wildcards) that pubs
	// // and subs are required to match (at least one once) in their
	// // respective lists to pair.
	// // Partitions are assigned at the publisher/subscriber level in
	// // the quality of service (QoS) settings.
	//
	// // Declare and instantiate a new SubscriberQos policy
	// SubscriberQos subscriberQos = new SubscriberQos();
	//
	// // Populate the SubscriberQos policy
	// participant.get_default_subscriber_qos(subscriberQos);
	//
	// subscriberQos.partition.name.clear();
	//
	// // Add an entry to the partition list. 'name' is actually a DDS
	// // StringSeq that you can simply .add() to.
	// // To receive OpenICE data for a specific patient, add the
	// // patient's MRN to the QoS partition policy
	// // e.g. "MRN=12345". MRNs are alphanumeric prefixed with "MRN="
	// // subscriberQos.partition.name.add("MRN=10101"); // This is
	// // fake patient Randall Jones in the MDPnP lab.
	//
	// // Partitioning supports some wildcards like "*" to access every
	// // partition (including the default or null partition)
	// subscriberQos.partition.name.add("*");
	//
	// // A note about partition names:
	// // The Supervisor uses an MRN for the Partition name and a First
	// // / Last name as a display name. For example, MRN=10101
	// // will show up as Randall Jones in the Supervisor. To match
	// // display names and MRNs, the Supervisor will either
	// // use defaults or look them up in an HL7 FHIR database. If you
	// // provide an HL7 FHIR server address, the Supervisor
	// // will treat that server as a "Master Patient Index". The
	// // Supervisor will attempt to download the Patient Resource
	// // (https://www.hl7.org/fhir/patient.html) from that address and
	// // use resource.identifier.value as the Partition name and
	// // resource.name.family / resource.name.given as the display
	// // name. If no HL7 FHIR address is provided, the Supervisor will
	// // provide a small SQL server of default names and MRNs.
	//
	// // Set the subscriber qos with our newly created SubscriberQos
	// participant.set_default_subscriber_qos(subscriberQos);
	//
	// // There are a couple ways to do this (as far as I can tell). I
	// // don't know which one is correct or standard or proper or
	// // whatever. For example:
	// // Subscriber subscriber =
	// // participant.get_implicit_subscriber();
	// // subscriber.get_qos(subscriberQos);
	// // subscriberQos.partition.name.add("MRN=10101");
	// // subscriber.set_qos(subscriberQos);
	//
	// // Same concept but this time for the publisher
	// PublisherQos publisherQos = new PublisherQos();
	//
	// participant.get_default_publisher_qos(publisherQos);
	//
	// publisherQos.partition.name.clear();
	//
	// // Change this line to the patient MRN for which you want to
	// // emit data.
	// // publisherQos.partition.name.add("MRN=10101");
	//
	// participant.set_default_publisher_qos(publisherQos);
	//
	// // Inform the participant about the sample array data type we
	// // would like to use in our endpoints
	// ice.SampleArrayTypeSupport.register_type(participant,
	// ice.SampleArrayTypeSupport.get_type_name());
	//
	// // Inform the participant about the numeric data type we would
	// // like to use in our endpoints
	// ice.NumericTypeSupport.register_type(participant,
	// ice.NumericTypeSupport.get_type_name());
	//
	// // A topic the mechanism by which reader and writer endpoints
	// // are matched.
	// Topic sampleArrayTopic =
	// participant.create_topic(ice.SampleArrayTopic.VALUE,
	// ice.SampleArrayTypeSupport.get_type_name(),
	// DomainParticipant.TOPIC_QOS_DEFAULT, null,
	// StatusKind.STATUS_MASK_NONE);
	//
	// // A second topic if for Numeric data
	// Topic numericTopic = participant.create_topic(ice.NumericTopic.VALUE,
	// ice.NumericTypeSupport.get_type_name(),
	// DomainParticipant.TOPIC_QOS_DEFAULT, null,
	// StatusKind.STATUS_MASK_NONE);
	//
	// ReceiveStrategy strategy = ReceiveStrategy.OnMiddlewareThread;
	//
	// // if(array.length > 1) {
	// // strategy = ReceiveStrategy.valueOf(array[1]);
	// // }
	//
	// System.out.println("strategy: " + strategy);
	//
	// switch (strategy) {
	// // receiveOnMyThreadByConditionVar demonstrates receiving data
	// // on *this* thread via notification by condition variable.
	// // Alternatively in unique cases readers can be polled at
	// // intervals with no signalling.
	// case OnMyThreadByConditionVar:
	// receiveOnMyThreadByConditionVar(participant, sampleArrayTopic,
	// numericTopic);
	// break;
	// // receiveOnMiddlewareThread demonstrates receiving data via a
	// // callback on a middleware thread.
	// case OnMiddlewareThread:
	// receiveOnMiddlewareThread(participant, sampleArrayTopic, numericTopic);
	// break;
	// case PublishExample:
	// sendOnThisThread(participant, sampleArrayTopic, numericTopic);
	// break;
	// }
	//
	// } catch (Exception e) {
	//
	// }
	// }
	//
	// }
	//
	// public static void receiveOnMyThreadByConditionVar(final
	// DomainParticipant participant,
	// final Topic sampleArrayTopic, final Topic numericTopic) {
	// // Create a reader endpoint for samplearray data
	// ice.SampleArrayDataReader saReader = (ice.SampleArrayDataReader)
	// participant.create_datareader_with_profile(
	// sampleArrayTopic, QosProfiles.ice_library, QosProfiles.waveform_data,
	// null,
	// StatusKind.STATUS_MASK_NONE);
	//
	// ice.NumericDataReader nReader = (ice.NumericDataReader)
	// participant.create_datareader_with_profile(numericTopic,
	// QosProfiles.ice_library, QosProfiles.numeric_data, null,
	// StatusKind.STATUS_MASK_NONE);
	//
	// // A waitset allows us to wait for various status changes in various
	// // entities
	// WaitSet ws = new WaitSet();
	//
	// // Here we configure the status condition to trigger when new data
	// // becomes available to the reader
	// saReader.get_statuscondition().set_enabled_statuses(StatusKind.DATA_AVAILABLE_STATUS);
	//
	// nReader.get_statuscondition().set_enabled_statuses(StatusKind.DATA_AVAILABLE_STATUS);
	//
	// // And register that status condition with the waitset so we can monitor
	// // its triggering
	// ws.attach_condition(saReader.get_statuscondition());
	//
	// ws.attach_condition(nReader.get_statuscondition());
	//
	// // will contain triggered conditions
	// ConditionSeq cond_seq = new ConditionSeq();
	//
	// // we'll wait as long as necessary for data to become available
	// Duration_t timeout = new Duration_t(Duration_t.DURATION_INFINITE_SEC,
	// Duration_t.DURATION_INFINITE_NSEC);
	//
	// // Will contain the data samples we read from the reader
	// ice.SampleArraySeq sa_data_seq = new ice.SampleArraySeq();
	//
	// ice.NumericSeq n_data_seq = new ice.NumericSeq();
	//
	// // Will contain the SampleInfo information about those data
	// SampleInfoSeq info_seq = new SampleInfoSeq();
	//
	// // This loop will repeat until the process is terminated
	// for (;;) {
	// // Wait for a condition to be triggered
	// ws.wait(cond_seq, timeout);
	// // Check that our status condition was indeed triggered
	// if (cond_seq.contains(saReader.get_statuscondition())) {
	// // read the actual status changes
	// int status_changes = saReader.get_status_changes();
	//
	// // Ensure that DATA_AVAILABLE is one of the statuses that
	// // changed in the DataReader.
	// // Since this is the only enabled status (see above) this is
	// // here mainly for completeness
	// if (0 != (status_changes & StatusKind.DATA_AVAILABLE_STATUS)) {
	// try {
	// // Read samples from the reader
	// saReader.read(sa_data_seq, info_seq,
	// ResourceLimitsQosPolicy.LENGTH_UNLIMITED,
	// SampleStateKind.NOT_READ_SAMPLE_STATE, ViewStateKind.ANY_VIEW_STATE,
	// InstanceStateKind.ALIVE_INSTANCE_STATE);
	//
	// // Iterator over the samples
	// for (int i = 0; i < info_seq.size(); i++) {
	// SampleInfo si = (SampleInfo) info_seq.get(i);
	// ice.SampleArray data = (ice.SampleArray) sa_data_seq.get(i);
	// // If the updated sample status contains fresh data
	// // that we can evaluate
	// if (si.valid_data) {
	// System.out.println("receiveOnMyThreadByConditionVar" + data);
	// }
	//
	// }
	// } catch (RETCODE_NO_DATA noData) {
	// // No Data was available to the read call
	// } finally {
	// // the objects provided by "read" are owned by the
	// // reader and we must return them
	// // so the reader can control their lifecycle
	// saReader.return_loan(sa_data_seq, info_seq);
	// }
	// }
	// }
	// if (cond_seq.contains(nReader.get_statuscondition())) {
	// // read the actual status changes
	// int status_changes = nReader.get_status_changes();
	//
	// // Ensure that DATA_AVAILABLE is one of the statuses that
	// // changed in the DataReader.
	// // Since this is the only enabled status (see above) this is
	// // here mainly for completeness
	// if (0 != (status_changes & StatusKind.DATA_AVAILABLE_STATUS)) {
	// try {
	// // Read samples from the reader
	// nReader.read(n_data_seq, info_seq,
	// ResourceLimitsQosPolicy.LENGTH_UNLIMITED,
	// SampleStateKind.NOT_READ_SAMPLE_STATE, ViewStateKind.ANY_VIEW_STATE,
	// InstanceStateKind.ALIVE_INSTANCE_STATE);
	//
	// // Iterator over the samples
	// for (int i = 0; i < info_seq.size(); i++) {
	// SampleInfo si = (SampleInfo) info_seq.get(i);
	// ice.Numeric data = (ice.Numeric) n_data_seq.get(i);
	// // If the updated sample status contains fresh data
	// // that we can evaluate
	// if (si.valid_data) {
	// if (data.metric_id.equals(rosetta.MDC_PULS_OXIM_SAT_O2.VALUE)) {
	// // This is an O2 saturation from pulse
	// // oximetry
	// // System.out.println("SpO2="+data.value);
	// } else if (data.metric_id.equals(rosetta.MDC_PULS_OXIM_PULS_RATE.VALUE))
	// {
	// // This is a pulse rate from pulse oximetry
	// // System.out.println("Pulse
	// // Rate="+data.value);
	// }
	// System.out.println("MDC_PULS_OXIM_SAT_O2" + data);
	// }
	//
	// }
	// } catch (RETCODE_NO_DATA noData) {
	// // No Data was available to the read call
	// } finally {
	// // the objects provided by "read" are owned by the
	// // reader and we must return them
	// // so the reader can control their lifecycle
	// nReader.return_loan(n_data_seq, info_seq);
	// }
	// }
	// }
	// }
	// }
	//
	// public void receiveOnMiddlewareThread(final DomainParticipant
	// participant, final Topic sampleArrayTopic,
	// final Topic numericTopic) {
	//
	// // A listener to receive callback events from the SampleArrayDataReader
	// final DataReaderListener saListener = new DataReaderListener() {
	//
	// @Override
	// public void on_data_available(DataReader reader) {
	// // Will contain the data samples we read from the reader
	// ice.SampleArraySeq sa_data_seq = new ice.SampleArraySeq();
	//
	// // Will contain the SampleInfo information about those data
	// SampleInfoSeq info_seq = new SampleInfoSeq();
	//
	// SampleArrayDataReader saReader = (SampleArrayDataReader) reader;
	//
	// // Read samples from the reader
	// try {
	// saReader.read(sa_data_seq, info_seq,
	// ResourceLimitsQosPolicy.LENGTH_UNLIMITED,
	// SampleStateKind.NOT_READ_SAMPLE_STATE, ViewStateKind.ANY_VIEW_STATE,
	// InstanceStateKind.ALIVE_INSTANCE_STATE);
	//
	// // Iterator over the samples
	// for (int i = 0; i < info_seq.size(); i++) {
	// SampleInfo si = (SampleInfo) info_seq.get(i);
	// ice.SampleArray data = (ice.SampleArray) sa_data_seq.get(i);
	// // If the updated sample status contains fresh data that
	// // we can evaluate
	// if (si.valid_data) {
	// storePatientData(null, participant.get_domain_id(), data);
	// // System.out.println("SAMPLE"+data);
	// // send data to cassandra
	// String patientId = "";
	// patientId =
	// MappingConstants.patientDomainMap.get(participant.get_domain_id());
	// executor.execute(new InsertDataThread(data, participant.get_domain_id(),
	// patientId));
	// // System.out.println("Sample ID:"+data.metric_id);
	// //
	// // System.out.println("ID->"+data.metric_id+"---"+data.values);
	// }
	// }
	// } catch (RETCODE_NO_DATA noData) {
	// // No Data was available to the read call
	// } finally {
	// // the objects provided by "read" are owned by the reader
	// // and we must return them
	// // so the reader can control their lifecycle
	// saReader.return_loan(sa_data_seq, info_seq);
	// }
	// }
	//
	// @Override
	// public void on_liveliness_changed(DataReader arg0,
	// LivelinessChangedStatus arg1) {
	// System.out.println("liveliness_changed " + arg1);
	// }
	//
	// @Override
	// public void on_requested_deadline_missed(DataReader arg0,
	// RequestedDeadlineMissedStatus arg1) {
	// System.out.println("requested_deadline_missed " + arg1);
	// }
	//
	// @Override
	// public void on_requested_incompatible_qos(DataReader arg0,
	// RequestedIncompatibleQosStatus arg1) {
	// System.out.println("requested_incompatible_qos " + arg1);
	// }
	//
	// @Override
	// public void on_sample_lost(DataReader arg0, SampleLostStatus arg1) {
	// System.out.println("sample_lost " + arg1);
	// }
	//
	// @Override
	// public void on_sample_rejected(DataReader arg0, SampleRejectedStatus
	// arg1) {
	// System.out.println("sample_rejected " + arg1);
	// }
	//
	// @Override
	// public void on_subscription_matched(DataReader arg0,
	// SubscriptionMatchedStatus arg1) {
	// System.out.println("subscription_matched " + arg1);
	// }
	//
	// };
	//
	// // A listener to receive callback events from the NumericDataReader
	// final DataReaderListener nListener = new DataReaderListener() {
	//
	// @Override
	// public void on_data_available(DataReader reader) {
	// ice.NumericSeq n_data_seq = new ice.NumericSeq();
	//
	// // Will contain the SampleInfo information about those data
	// SampleInfoSeq info_seq = new SampleInfoSeq();
	//
	// NumericDataReader nReader = (NumericDataReader) reader;
	//
	// try {
	// // Read samples from the reader
	// nReader.read(n_data_seq, info_seq,
	// ResourceLimitsQosPolicy.LENGTH_UNLIMITED,
	// SampleStateKind.NOT_READ_SAMPLE_STATE, ViewStateKind.ANY_VIEW_STATE,
	// InstanceStateKind.ALIVE_INSTANCE_STATE);
	//
	// // Iterator over the samples
	// for (int i = 0; i < info_seq.size(); i++) {
	// SampleInfo si = (SampleInfo) info_seq.get(i);
	// ice.Numeric data = (ice.Numeric) n_data_seq.get(i);
	// // If the updated sample status contains fresh data that
	// // we can evaluate
	// if (si.valid_data) {
	// if (data.metric_id.equals(rosetta.MDC_PULS_OXIM_SAT_O2.VALUE)) {
	// // This is an O2 saturation from pulse oximetry
	// // System.out.println("SpO2="+data.value);
	// } else if (data.metric_id.equals(rosetta.MDC_PULS_OXIM_PULS_RATE.VALUE))
	// {
	// // This is a pulse rate from pulse oximetry
	// // System.out.println("Pulse Rate="+data.value);
	// }
	// // System.out.println("Numeric data ::"+data);
	// if (data != null) {
	// storePatientData(data, participant.get_domain_id(), null);
	//
	// // Runnable worker ()= new
	// // InsertPatientDataThread(data);
	// // System.out.println("ID:"+data.metric_id);
	//
	// String patientId = "";
	// patientId =
	// MappingConstants.patientDomainMap.get(participant.get_domain_id());
	// executor.execute(new InsertDataThread(data, participant.get_domain_id(),
	// patientId));
	//
	// maxExecutor.execute(new CalculateVitals(data,
	// participant.get_domain_id(), patientId));
	//
	// }
	// }
	// }
	// } catch (RETCODE_NO_DATA noData) {
	// // No Data was available to the read call
	// } finally {
	// // the objects provided by "read" are owned by the reader
	// // and we must return them
	// // so the reader can control their lifecycle
	// nReader.return_loan(n_data_seq, info_seq);
	// }
	//
	// }
	//
	// private void createMonitorDataObject(Numeric data) {
	// PatientData pd = new PatientData();
	// Date date = new Date();
	// pd.setPatientId("1");
	//
	// pd.setEventTime(date);
	//
	// String uniDevId = data.unique_device_identifier;
	// if (uniDevId != null && !uniDevId.isEmpty()) {
	//
	// }
	// String metricId = data.metric_id;
	// if (metricId != null && !metricId.isEmpty()) {
	// pd.setMetricId(metricId);
	// }
	// String vendorId = data.vendor_metric_id;
	// if (vendorId != null && !vendorId.isEmpty()) {
	// pd.setVendorMetricId(vendorId);
	// }
	// int instanceId = data.instance_id;
	// pd.setInstanceId(String.valueOf(instanceId));
	//
	// String unitId = data.unit_id;
	// if (unitId != null && !unitId.isEmpty()) {
	// pd.setUnitId(unitId);
	// }
	// float value = data.value;
	// pd.setValue(String.valueOf(value));
	//
	// Time_t devTime = data.device_time;
	// if (devTime != null) {
	// int devSec = devTime.sec;
	// pd.setDeviceTimeSec(String.valueOf(devSec));
	// int devNanoSec = devTime.nanosec;
	// pd.setDeviceTimeNano(String.valueOf(devNanoSec));
	// }
	//
	// Time_t presTime = data.presentation_time;
	// if (presTime != null) {
	// int presSec = presTime.sec;
	// pd.setPresTimeSec(String.valueOf(presSec));
	// int presNanoSec = presTime.nanosec;
	// pd.setPresTimeNano(String.valueOf(presNanoSec));
	// }
	// deviceDAO.startService(pd);
	//
	// }
	//
	// @Override
	// public void on_liveliness_changed(DataReader arg0,
	// LivelinessChangedStatus arg1) {
	// System.out.println("liveliness_changed " + arg1);
	// }
	//
	// @Override
	// public void on_requested_deadline_missed(DataReader arg0,
	// RequestedDeadlineMissedStatus arg1) {
	// System.out.println("requested_deadline_missed " + arg1);
	// }
	//
	// @Override
	// public void on_requested_incompatible_qos(DataReader arg0,
	// RequestedIncompatibleQosStatus arg1) {
	// System.out.println("requested_incompatible_qos " + arg1);
	// }
	//
	// @Override
	// public void on_sample_lost(DataReader arg0, SampleLostStatus arg1) {
	// System.out.println("sample_lost " + arg1);
	// }
	//
	// @Override
	// public void on_sample_rejected(DataReader arg0, SampleRejectedStatus
	// arg1) {
	// System.out.println("sample_rejected " + arg1);
	// }
	//
	// @Override
	// public void on_subscription_matched(DataReader arg0,
	// SubscriptionMatchedStatus arg1) {
	// System.out.println("subscription_matched " + arg1);
	// }
	// };
	//
	// // Create a reader endpoint for samplearray data
	//
	// ice.SampleArrayDataReader saReader = (ice.SampleArrayDataReader)
	// participant.create_datareader_with_profile(
	// sampleArrayTopic, QosProfiles.ice_library, QosProfiles.waveform_data,
	// saListener,
	// StatusKind.STATUS_MASK_ALL);
	//
	// @SuppressWarnings("unused")
	// ice.NumericDataReader nReader = (ice.NumericDataReader)
	// participant.create_datareader_with_profile(numericTopic,
	// QosProfiles.ice_library, QosProfiles.numeric_data, nListener,
	// StatusKind.STATUS_MASK_ALL);
	//
	// }
	//
	// public static void sendOnThisThread(DomainParticipant participant, Topic
	// sampleArrayTopic, Topic numericTopic)
	// throws InterruptedException {
	//
	// // Creates a data writer; uses the default implicit publisher for this
	// // participant
	// ice.SampleArrayDataWriter saWriter = (ice.SampleArrayDataWriter)
	// participant.create_datawriter_with_profile(
	// sampleArrayTopic, QosProfiles.ice_library, QosProfiles.waveform_data,
	// null,
	// StatusKind.STATUS_MASK_NONE);
	//
	// ice.NumericDataWriter nWriter = (ice.NumericDataWriter)
	// participant.create_datawriter_with_profile(numericTopic,
	// QosProfiles.ice_library, QosProfiles.numeric_data, null,
	// StatusKind.STATUS_MASK_NONE);
	//
	// // Populate the values of a sample to be written later
	// ice.Numeric numeric = new ice.Numeric();
	// numeric.unique_device_identifier = "1111";
	// numeric.metric_id = rosetta.MDC_ECG_HEART_RATE.VALUE;
	// numeric.unit_id = rosetta.MDC_DIM_BEAT_PER_MIN.VALUE;
	// numeric.vendor_metric_id = "";
	// numeric.instance_id = 0;
	// numeric.device_time = new Time_t();
	// numeric.presentation_time = new Time_t();
	//
	// ice.SampleArray sampleArray = new ice.SampleArray();
	// sampleArray.unique_device_identifier = "1111";
	// sampleArray.metric_id = ice.MDC_ECG_LEAD_I.VALUE;
	// sampleArray.unit_id = rosetta.MDC_DIM_MILLI_VOLT.VALUE;
	// sampleArray.vendor_metric_id = "";
	// sampleArray.instance_id = 0;
	// sampleArray.device_time = new Time_t();
	// sampleArray.presentation_time = new Time_t();
	// sampleArray.frequency = 10;
	//
	// // Preregistering instances speeds up subsequent writes
	// InstanceHandle_t saHandle = saWriter.register_instance(sampleArray);
	// InstanceHandle_t nHandle = nWriter.register_instance(numeric);
	//
	// // Write
	// for (int i = 0; i < 10; i++) {
	// long time = System.currentTimeMillis();
	//
	// numeric.device_time.sec = (int) (time / 1000L);
	// numeric.device_time.nanosec = (int) (time % 1000L * 1000000L);
	// numeric.presentation_time.copy_from(numeric.device_time);
	// nWriter.write(numeric, nHandle);
	//
	// sampleArray.values.clear();
	//
	// sampleArray.device_time.copy_from(numeric.device_time);
	// sampleArray.presentation_time.copy_from(numeric.presentation_time);
	//
	// // Square wave
	// if (0 == (i % 2)) {
	// for (int j = 0; j < 10; j++) {
	// sampleArray.values.userData.add(0.0f);
	// }
	// } else {
	// for (int j = 0; j < 10; j++) {
	// sampleArray.values.userData.add(1.0f);
	// }
	//
	// }
	// saWriter.write(sampleArray, saHandle);
	//
	// System.out.println("Wrote " + numeric);
	// System.out.println(sampleArray);
	//
	// Thread.sleep(1000L);
	// }
	//
	// saWriter.unregister_instance(sampleArray, saHandle);
	// nWriter.unregister_instance(numeric, nHandle);
	//
	// }

	enum ReceiveStrategy {
		OnMyThreadByConditionVar, OnMiddlewareThread, PublishExample,
	}

	@Override
	public List<PatientDataJsonObject> getPatientData(String id) {
		return deviceDAO.getData(id);
	}

	@Override
	public MonitorJSON getPatientECGData(String id) {
		return deviceDAO.getECGData(id);
	}

	@Override
	public MonitorJSON getPatientBPData(String id) {
		return deviceDAO.getBPData(id);
	}

	@Override
	public MonitorJSON getPatientPulseData(String id) {
		return deviceDAO.getPulseData(id);
	}

	@Override
	public List<PatientDataJsonObject> getPatientVariableData(String id) {
		return deviceDAO.getVariableData(id);
	}

	public class InsertDataThread implements Runnable {

		private String data;
		private String sampleData;
		private int domainId;
		private String patientId;
		private Date date;
		private String serverDate;
		private String infinityData;
		private String uhid;
		private String jsonData;
		private String devicename;
		private boolean isiNICU = false;
		
		private Date geB450date;
	    private String carescapeACKData;
	    private String finalPatientID;
	    private String finalBedID;
	    private String timestamp;

		// public InsertDataThread(String data, int domainId, String patientId)
		// {
		// this.data = data;
		// this.domainId = domainId;
		// this.patientId = patientId;
		// }

		// public InsertDataThread(String sampleData, int domainId, String
		// patientId) {
		// this.sampleData = sampleData;
		// this.domainId = domainId;
		// this.patientId = patientId;
		// }

		// this is called when data arrives from
		// infinity gateway.
		public InsertDataThread(Date date, String data2, String uhid) {
			this.date = date;
			this.infinityData = data2;
			this.uhid = uhid;
		}
		
		public InsertDataThread(String finalPatientID, String finalBedID, String timestamp, Date date2, String data) {
		      this.finalPatientID = finalPatientID;
		      this.finalBedID = finalBedID;
		      this.timestamp = timestamp;
		      this.geB450date = date2;
		      this.carescapeACKData = data;
		}

		// this is called when data arrives from pushKafkaDataToCassandra
		public InsertDataThread(Date date, String data, String uhid, boolean isiNICU, String deviceName) {
			this.date = date;
			this.jsonData = data;
			this.isiNICU = isiNICU;
			this.devicename = deviceName;
			this.uhid = uhid;
		}

		// this is called when data arrives from
		// inicu medical devices via rest Api's
		public InsertDataThread(Date date, String serverDate, String data, String uhid, boolean isiNICU,
								String deviceName) {
			this.date = date;
			this.serverDate = serverDate;
			this.jsonData = data;
			this.isiNICU = isiNICU;
			this.devicename = deviceName;
			this.uhid = uhid;
		}

		@Override
		public void run() {
			String patientId = "161203970";
			String isCarescapeACK = BasicConstants.props.getProperty(BasicConstants.PROP_CARESCAPE_ACK_LISTENER);
			String isInfinityTcpListener = BasicConstants.props.getProperty(BasicConstants.PROP_INFINITY_TCP_LISTENER);
			if (!BasicUtils.isEmpty(this.patientId)) {
				patientId = this.patientId;
			}
			if (data != null) {
				// PatientData pd = new PatientData();
				// PatientMonitorData pd = new PatientMonitorData();
				// Date date = new Date();
				// pd.setPatientId(patientId);
				//
				// pd.setEventTime(date);
				//
				// String uniDevId = data.unique_device_identifier;
				// if (uniDevId != null && !uniDevId.isEmpty()) {
				//
				// }
				// String metricId = data.metric_id;
				// if (metricId != null && !metricId.isEmpty()) {
				// pd.setMetricId(metricId);
				// }
				// String vendorId = data.vendor_metric_id;
				// if (vendorId != null && !vendorId.isEmpty()) {
				// pd.setVendorMetricId(vendorId);
				// }
				// int instanceId = data.instance_id;
				// pd.setInstanceId(String.valueOf(instanceId));
				//
				// String unitId = data.unit_id;
				// if (unitId != null && !unitId.isEmpty()) {
				// pd.setUnitId(unitId);
				// }
				// float value = data.value;
				// pd.setValue(String.valueOf(value));
				//
				// Time_t devTime = data.device_time;
				// if (devTime != null) {
				// int devSec = devTime.sec;
				// pd.setDeviceTimeSec(String.valueOf(devSec));
				// int devNanoSec = devTime.nanosec;
				// pd.setDeviceTimeNano(String.valueOf(devNanoSec));
				// }
				//
				// Time_t presTime = data.presentation_time;
				// if (presTime != null) {
				// int presSec = presTime.sec;
				// pd.setPresTimeSec(String.valueOf(presSec));
				// int presNanoSec = presTime.nanosec;
				// pd.setPresTimeNano(String.valueOf(presNanoSec));
				// }
				//
				// pd.setValueType(BasicConstants.VALUE_TYPE_NUMERIC);
				// pd.setDomainId(String.valueOf(domainId));
				// testDAO.startService(pd);
				// deviceDAO.createPatientMonitorData(pd);
				// pushDataKafka(pd);

			} else if (this.sampleData != null) {
				// PatientMonitorData pd = new PatientMonitorData();
				// Date date = new Date();
				// pd.setPatientId(patientId);
				//
				// pd.setEventTime(date);
				// String metricId = sampleData.metric_id;
				// if (metricId != null && !metricId.isEmpty()) {
				// pd.setMetricId(metricId);
				// }
				// String vendorId = sampleData.vendor_metric_id;
				// if (vendorId != null && !vendorId.isEmpty()) {
				// pd.setVendorMetricId(vendorId);
				// }
				// int instanceId = sampleData.instance_id;
				// pd.setInstanceId(String.valueOf(instanceId));
				//
				// String unitId = sampleData.unit_id;
				// if (unitId != null && !unitId.isEmpty()) {
				// pd.setUnitId(unitId);
				// }
				// String values = "";
				// Values value = sampleData.values;
				// FloatSeq userData = value.userData;
				// Iterator itr = userData.iterator();
				// while (itr.hasNext()) {
				// Object element = itr.next();
				// if (!values.isEmpty()) {
				// values = values + "," + element.toString();
				// } else {
				// values = element.toString();
				// }
				// }
				// pd.setValue(String.valueOf(values));
				//
				// Time_t devTime = sampleData.device_time;
				// if (devTime != null) {
				// int devSec = devTime.sec;
				// pd.setDeviceTimeSec(String.valueOf(devSec));
				// int devNanoSec = devTime.nanosec;
				// pd.setDeviceTimeNano(String.valueOf(devNanoSec));
				// }
				//
				// Time_t presTime = sampleData.presentation_time;
				// if (presTime != null) {
				// int presSec = presTime.sec;
				// pd.setPresTimeSec(String.valueOf(presSec));
				// int presNanoSec = presTime.nanosec;
				// pd.setPresTimeNano(String.valueOf(presNanoSec));
				// }
				//
				// pd.setValueType(BasicConstants.VALUE_TYPE_SAMPLE);
				// pd.setDomainId(String.valueOf(domainId));
				// testDAO.startService(pd);
				// deviceDAO.createPatientMonitorData(pd);
				// pushDataKafka(pd);

			} else if (isiNICU) {
				// data needs to be pushed to iNICU database
				// the data is received from inicu medical device via Rest Api.

				PatientDeviceDataInicu pd = new PatientDeviceDataInicu();
				pd.setUhid(uhid);
				pd.setDeviceTime(date);
				pd.setServerTime(serverDate);
				pd.setDeviceType(devicename);
				pd.setData(jsonData);
				deviceDAO.inseriNICURecord(pd);
			} else if (isInfinityTcpListener.equalsIgnoreCase("true")) {
				// data insertion using infinitygateway
				DeviceDataInfinity dev = new DeviceDataInfinity();
				dev.setUhid(uhid);

				if (!BasicUtils.isEmpty(infinityData)) {
					dev.setValue(infinityData);
				}

				if (!BasicUtils.isEmpty(date)) {
					dev.setDeviceTime(date);
				}

				deviceDAO.create(dev);
			}
			if (isCarescapeACK != null && !isCarescapeACK.isEmpty()) {
		        if (isCarescapeACK.equalsIgnoreCase("true")) {
		          // data insertion using CarescapeACK gateway
		          DeviceDataCarescapeACK dev = new DeviceDataCarescapeACK();

		          if (!BasicUtils.isEmpty(finalPatientID)) {

		            dev.setPatientId(finalPatientID);
		          }
		          if (!BasicUtils.isEmpty(finalBedID)) {

		            dev.setBedId(finalBedID);
		          }
		          if (!BasicUtils.isEmpty(timestamp)) {

		            dev.setTimestamp(timestamp);
		          }
		          if (!BasicUtils.isEmpty(geB450date)) {

		            dev.setDate(geB450date);
		          }
		          if (!BasicUtils.isEmpty(carescapeACKData)) {

		            dev.setData(carescapeACKData);
		          }
//		        System.out.println("creating table entry");
		          deviceDAO.create(dev);
		        }
		      }
		}
	}

	/**
	 * An independent thread calculates max values from the static variable. A
	 * thread pool calculates max while pushing the data. migrates data from
	 * cassandra to postgres migrates maximum value
	 */
	@Override
	public void migrateDataToPostgres() {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// push data to postgres every minute
				while (true) {
					try {
						System.out.println("Thread initiated");
						Thread.sleep(60000);
						userPanelService.migrateDataToPostgres();
						// clear Hashmap
						MappingConstants.patientDataMap = new HashMap<String, HashMap<String, String>>();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}

	// public class CalculateVitals implements Runnable {
	// private Numeric data;
	// private int domainId;
	// private String patientId;
	//
	// public CalculateVitals(Numeric numericData, int domainId, String
	// patientId) {
	// this.data = numericData;
	// this.domainId = domainId;
	// this.patientId = patientId;
	// }
	//
	// @Override
	// public void run() {
	// if (!BasicUtils.isEmpty(data)) {
	// // patientid hardcoded as 1161203970.
	// String patientId = "161203970";
	// if (!BasicUtils.isEmpty(this.patientId)) {
	// patientId = this.patientId;
	// }
	//
	// // fetch patient id from domain id.
	//
	// String metricId = data.metric_id;
	// float value = data.value;
	//
	// if (!BasicUtils.isEmpty(metricId) && !BasicUtils.isEmpty(value)) {
	// mapDataValues = MappingConstants.patientDataMap.get(patientId);
	// if (!BasicUtils.isEmpty(mapDataValues)) {
	// // update max min mean median values.
	// String vitalVal =
	// mapDataValues.get(MappingConstants.metricMap.get(metricId.trim()));
	// if (BasicUtils.isEmpty(vitalVal)) {
	// // add new entry
	// vitalVal = String.valueOf(value);
	// // push value in the map.
	// mapDataValues.put(MappingConstants.metricMap.get(metricId.trim()),
	// vitalVal);
	// MappingConstants.patientDataMap.get(patientId).putAll(mapDataValues);
	// } else {
	// // update the values to calculate max min mean and
	// // median
	// HashMap<String, String> updatedMap = updateMaxValue(mapDataValues,
	// metricId.trim(), value);
	// MappingConstants.patientDataMap.get(patientId).putAll(updatedMap);
	// }
	//
	// } else {
	// // add new entry to the dataset
	// HashMap<String, String> patientVitalsMap = new HashMap<>();
	// patientVitalsMap.put(MappingConstants.metricMap.get(metricId.trim()),
	// String.valueOf(value));
	// MappingConstants.patientDataMap.put(patientId, patientVitalsMap);
	// }
	// }
	// }
	// }
	//
	// private HashMap<String, String> updateMaxValue(HashMap<String, String>
	// mapDataValues, String metricId,
	// float currValue) {
	// String val = mapDataValues.get(MappingConstants.metricMap.get(metricId));
	// if (!BasicUtils.isEmpty(val)) {
	// float existVal = Float.parseFloat(val);
	// if (currValue > existVal) {
	// mapDataValues.put(MappingConstants.metricMap.get(metricId),
	// String.valueOf(currValue));
	// }
	// }
	// return mapDataValues;
	// }
	// }

	/**
	 * Philips monitor data acquisition layer.
	 */
	@Override
	public ResponseMessageObject startPhilipsMonitoring(AddDeviceTemplateJson devData) {
		ResponseMessageObject rObj = new ResponseMessageObject();
		rObj.setType(BasicConstants.MESSAGE_SUCCESS);
		try {
			rObj = devDataValidator(devData);
			if (rObj.getType().equalsIgnoreCase(BasicConstants.MESSAGE_SUCCESS)) {
				{
					/*
					 *
					 * //add device to the database String devId = null; DeviceDetail devDetail =
					 * new DeviceDetail(); devDetail.setAvailable(true);
					 * devDetail.setDevicebrand("Philips"); devDetail.setDevicetype("monitor");
					 * devDetail.setDomainid(devData.getDomainId());
					 * devDetail.setIpaddress(devData.getIpAddress()); try{ devDetail =
					 * (DeviceDetail)patientDao.saveObject(devDetail); String query =
					 * "SELECT deviceid FROM device_detail WHERE ipaddress='"
					 * +devDetail.getIpaddress()+"'"; List<String> result =
					 * patientDao.getListFromNativeQuery(query); if(!BasicUtils.isEmpty(result)){
					 * devId = String.valueOf(result.get(0)); } }catch(Exception e){
					 * e.printStackTrace(); }
					 *
					 * if(!BasicUtils.isEmpty(devId)){ BedDeviceDetail bedDevDetail = new
					 * BedDeviceDetail(); bedDevDetail.setBedid(devData.getBedId());
					 * bedDevDetail.setDeviceid(devId); try{ patientDao.saveObject(bedDevDetail);
					 * }catch(Exception e){ // roll back device entry. e.printStackTrace(); } }
					 */

					// configuration to acquire data via rj45.
					Integer domainId = Integer.parseInt(devData.getDomainId());
					// new
					// PhilipsMonitorDataAcquisition().startMonitoring(devData.getIpAddress(),
					// domainId);

					/*
					 *
					 *
					 * //provide list of domain id's List<String> domainIds = new
					 * ArrayList<String>(); domainIds.add(String.valueOf(domainId));
					 * startMonitorService(domainIds);
					 *
					 */

					// maintain local static patient id and domain id map
					// query to fetch the uhid corresponding to the domain id.
					// try{
					// String query = "SELECT uhid from bed_device_history where
					// domainid='"+domainId+"';";
					// List<String> result =
					// patientDao.getListFromNativeQuery(query);
					// if(!BasicUtils.isEmpty(result)){
					// String uhid = result.get(0).toString();
					// MappingConstants.patientDomainMap.put(uhid,
					// String.valueOf(domainId));
					// }
					// }catch(Exception e){
					// e.printStackTrace();
					// // rollback everything.
					// }

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			rObj.setType(BasicConstants.MESSAGE_FAILURE);
		}
		return rObj;
	}

	private ResponseMessageObject devDataValidator(AddDeviceTemplateJson devData) {
		ResponseMessageObject rObj = new ResponseMessageObject();
		rObj.setMessage(BasicConstants.MESSAGE_SUCCESS);
		rObj.setType(BasicConstants.MESSAGE_SUCCESS);
		if (BasicUtils.isEmpty(devData.getDate())) {
			rObj.setMessage("Date is empty");
			rObj.setType(BasicConstants.MESSAGE_FAILURE);
		} else if (BasicUtils.isEmpty(devData.getIpAddress())) {
			rObj.setMessage("IP address is empty");
			rObj.setType(BasicConstants.MESSAGE_FAILURE);
		} else if (BasicUtils.isEmpty(devData.getUhid())) {
			rObj.setMessage("UHID is empty");
			rObj.setType(BasicConstants.MESSAGE_FAILURE);
		} else if (BasicUtils.isEmpty(devData.getDomainId())) {
			rObj.setMessage("Domain id cannot be empty.");
			rObj.setType(BasicConstants.MESSAGE_FAILURE);
		}

		if (!BasicUtils.isEmpty(devData.getIpAddress())) {
			// check for a valid ip address.
			Pattern pattern;
			Matcher matcher;
			pattern = Pattern.compile(BasicConstants.IPADDRESS_PATTERN);
			matcher = pattern.matcher(devData.getIpAddress());
			if (!matcher.matches()) {
				rObj.setMessage("not a valid ip address");
				rObj.setType(BasicConstants.MESSAGE_FAILURE);
			} else {
				// check if ip address is already not assigned.
				boolean alreadyExists = patientDeviceService.checkifIpExists(devData.getIpAddress());
				if (alreadyExists) {
					rObj.setMessage("assigned Ip address already exists");
					rObj.setType(BasicConstants.MESSAGE_FAILURE);
				}
			}
		}
		return rObj;
	}

	@Override
	public AddDeviceMasterJson getDeviceTemplateData(String uhid, String bedid, String branchName) {
		AddDeviceMasterJson masterJson = new AddDeviceMasterJson();
		AddDeviceTemplateJson templateJson = new AddDeviceTemplateJson();
		AddDeviceDropDowns dropDowns = new AddDeviceDropDowns();
		List<AddDeviceListJson> listDevice = new ArrayList<>();
		List<AddDeviceListJson> pastDevice = new ArrayList<>();
		List<AddDeviceListJson> currentDevice = new ArrayList<>();
		// Integer domainId = patientDeviceService.getUniqueDomainId();
		// templateJson.setDomainId(String.valueOf(domainId));
		templateJson.setBedId(bedid);
		templateJson.setUhid(uhid);

		// add dropDowns
		List<KeyValueObj> refInicuBboxUnmappedDeviceList = new ArrayList<>();
		List<KeyValueObj> tinyInicuBboxUnmappedDeviceList = new ArrayList<>();
		String RefInicuBboxListQuery = "SELECT obj FROM RefInicuBbox as obj where branchname = '"
				+ branchName + "' and active='true'";
		List<RefInicuBbox> RefInicuBboxList = patientDao.getListFromMappedObjNativeQuery(RefInicuBboxListQuery);
		if (!BasicUtils.isEmpty(RefInicuBboxList)) {
			for (RefInicuBbox refInicuBbox : RefInicuBboxList) {
				String bedDeviceDetailListQuery = "SELECT obj FROM BedDeviceDetail as obj "
						+ "WHERE status ='true' AND time_to is null AND bbox_device_id='"
						+ refInicuBbox.getBboxId() + "'";
				List<BedDeviceDetail> bedDeviceDetailList = patientDao.getListFromMappedObjNativeQuery(bedDeviceDetailListQuery);
				if (!(bedDeviceDetailList != null && bedDeviceDetailList.size() > 0)) {
					KeyValueObj refInicuBboxUnmappedDeviceListObj = new KeyValueObj();
					if (!BasicUtils.isEmpty(refInicuBbox.getBboxname())) {
						refInicuBboxUnmappedDeviceListObj.setKey(refInicuBbox.getBboxId());
						refInicuBboxUnmappedDeviceListObj.setValue(refInicuBbox.getBboxname());
						refInicuBboxUnmappedDeviceList.add(refInicuBboxUnmappedDeviceListObj);
					} else if (BasicUtils.isEmpty(refInicuBbox.getBboxname()) && !BasicUtils.isEmpty(refInicuBbox.getTinyboxname())) {
						refInicuBboxUnmappedDeviceListObj.setKey(refInicuBbox.getBboxId());
						refInicuBboxUnmappedDeviceListObj.setValue(refInicuBbox.getTinyboxname());
						tinyInicuBboxUnmappedDeviceList.add(refInicuBboxUnmappedDeviceListObj);
					}
				}
			}
		}

		// HashMap<String,List<String>> brandMap = new HashMap<>();
		// String fetchbrand = "select dev.deviceid, "
		// + "dev.devicename, "
		// + "dev.brandname, "
		// + "devtype.device_type "
		// + "FROM ref_inicu_devices as dev, ref_device_type as devtype "
		// + "where dev.devicetypeid = devtype.devicetypeid order by
		// dev.deviceid";
		//
		// List<Object[]> result =
		// patientDao.getListFromNativeQuery(fetchbrand);
		// if(!BasicUtils.isEmpty(result)){
		// for(Object[] obj: result){
		// String devName = obj[3].toString();
		// if(!BasicUtils.isEmpty(devName)){
		// if(!BasicUtils.isEmpty(brandMap.get(devName))){
		// List<String> listDev = brandMap.get(devName);
		// if(!BasicUtils.isEmpty(listDev)){
		// listDev.add(obj[1].toString()+" | "+obj[2].toString());
		// }
		// brandMap.put(devName, listDev);
		// }else{
		// List<String> listDevices = new ArrayList<>();
		// listDevices.add(obj[1].toString()+" | "+obj[2].toString());
		// brandMap.put(devName,listDevices);
		// }
		// }
		// }
		// }

		dropDowns.setDeviceName(refInicuBboxUnmappedDeviceList);
		dropDowns.setDeviceName2(refInicuBboxUnmappedDeviceList);
		dropDowns.setTinyDeviceNames(tinyInicuBboxUnmappedDeviceList);

		// dropDowns.setBrandName(brandMap);

		masterJson.setDropDowns(dropDowns);

		// get list of older connected devices.
		// String query = "SELECT creationtime, devicetype,devicebrand,
		// available, deviceid, endtime from bed_device_history where uhid = '"
		// + uhid.trim() + "'";
		// List<Object[]> resultSet = patientDao.getListFromNativeQuery(query);
		// if (!BasicUtils.isEmpty(resultSet)) {
		// for (Object[] objArr : resultSet) {
		// AddDeviceListJson devJson = new AddDeviceListJson();
		// String creationTime = objArr[0].toString();
		// try {
		// DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
		// Locale.ENGLISH);
		// Date date = format.parse(creationTime);
		//
		// if (!BasicUtils.isEmpty(date)) {
		// DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy",
		// Locale.ENGLISH);
		// devJson.setDate(dateFormat.format(date));
		// DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss",
		// Locale.ENGLISH);
		// devJson.setTimeFrom(timeFormat.format(date));
		//
		// if(objArr[5]!=null){
		// String endTime = objArr[5].toString();
		// if(!BasicUtils.isEmpty(endTime)){
		// Date endDate = format.parse(endTime);
		// devJson.setTimeTo(timeFormat.format(endDate));
		// }
		// }
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// if(!BasicUtils.isEmpty(objArr[1])){
		// String deviceType = objArr[1].toString();
		// if (!BasicUtils.isEmpty(deviceType)) {
		// devJson.setDevice(" " + deviceType);
		// }
		// }

		// if(!BasicUtils.isEmpty(objArr[2])){
		// String deviceBrand = objArr[2].toString();
		// if (!BasicUtils.isEmpty(deviceBrand) &&
		// !BasicUtils.isEmpty(deviceType)) {
		// devJson.setDevice(deviceType + " " + deviceBrand);
		// } else {
		// devJson.setDevice(deviceBrand);
		// }
		// }

		// String deviceId = objArr[4].toString();
		// if (!BasicUtils.isEmpty(deviceId)) {
		// devJson.setDeviceId(deviceId);
		// }
		//
		// Boolean isConnected = (Boolean) objArr[3];
		// if (!BasicUtils.isEmpty(isConnected)) {
		// if (isConnected) {
		// devJson.setConnected(true);
		// listDevice.add(devJson);
		// } else {
		// devJson.setConnected(false);
		// pastDevice.add(devJson);
		// }
		// } else {
		// pastDevice.add(devJson);
		// }
		// }
		// }

		// fetch older boxes.
		List<KeyValueObj> currentDeviceList = new ArrayList<>();
		List<Integer> devices = new ArrayList<>();
		List<Integer> tinyDevices = new ArrayList<>();
		String query = "SELECT obj FROM BedDeviceDetail as obj WHERE obj.uhid='" + uhid + "' ";
		List<BedDeviceDetail> resultSet = patientDao.getListFromMappedObjNativeQuery(query);
		int offset = (TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE)
				.getRawOffset() - TimeZone.getDefault().getRawOffset());
		Timestamp timeFromWithOffset = null;
		Timestamp timeToWithOffset = null;
		if (!BasicUtils.isEmpty(resultSet)) {
			for (BedDeviceDetail beddev : resultSet) {
				AddDeviceListJson devJson = new AddDeviceListJson();
				// get inicu medical device name
				String getDeviceName = "SELECT obj FROM RefInicuBbox as obj WHERE obj.bboxId='"
						+ beddev.getBbox_device_id() + "' ";
				List<RefInicuBbox> result = patientDao.getListFromMappedObjNativeQuery(getDeviceName);
				if (!BasicUtils.isEmpty(result)) {
					if (!beddev.isStatus()) {
						devJson.setConnected(beddev.isStatus());
						devJson.setDate(beddev.getCreationtime().toString());
						if (!BasicUtils.isEmpty(result.get(0).getBboxname())) {
							devJson.setDevice(result.get(0).getBboxname());
						} else {
							devJson.setDevice(result.get(0).getTinyboxname());
						}

						if (!BasicUtils.isEmpty(beddev.getTime_from())) {
							timeFromWithOffset = new Timestamp(beddev.getTime_from().getTime() + offset);
							devJson.setTimeFrom(timeFromWithOffset.toString());
						}
						if (!BasicUtils.isEmpty(beddev.getTime_to())) {
							timeToWithOffset = new Timestamp(beddev.getTime_to().getTime() + offset);
							devJson.setTimeTo(timeToWithOffset.toString());
						}
						pastDevice.add(devJson);
					} else {
						KeyValueObj currentDeviceListObj = new KeyValueObj();
						currentDeviceListObj.setKey(result.get(0).getBboxId());
						currentDeviceListObj.setDateTime(beddev.getTime_from());

						if (!BasicUtils.isEmpty(result.get(0).getBboxname())) {
							currentDeviceListObj.setValue(result.get(0).getBboxname());
							currentDeviceList.add(currentDeviceListObj);
							devices.add(beddev.getBbox_device_id().intValue());
						} else {
							currentDeviceListObj.setValue(result.get(0).getTinyboxname());
							masterJson.setTinyCurrentDevice(currentDeviceListObj);
							tinyDevices.add(beddev.getBbox_device_id().intValue());
						}
					}
				}
			}
		}

		String babyDetailQuery = "SELECT obj FROM BabyDetail as obj WHERE obj.uhid='"
				+ uhid + "' order by obj.creationtime desc";
		List<BabyDetail> babyDetailResultSet = patientDao.getListFromMappedObjNativeQuery(babyDetailQuery);
		BabyDetail babyDetail = null;
		if (!BasicUtils.isEmpty(babyDetailResultSet)) {
			babyDetail = babyDetailResultSet.get(0);
			templateJson.setHisDischargeDate(babyDetail.getHisdischargedate());

			Date dateOfAdmission = babyDetail.getDateofadmission();
			String timeOfAdmission = babyDetail.getTimeofadmission();
			String admDate = dateOfAdmission + "";
			if (!BasicUtils.isEmpty(timeOfAdmission)) {
				timeOfAdmission = timeOfAdmission.replaceFirst(",", ":");
				timeOfAdmission = timeOfAdmission.replace(",", " ");
				admDate += " " + timeOfAdmission;

				templateJson.setAdmissionDate(admDate);
			}
		}

		templateJson.setDeviceList(devices);
		templateJson.setTinyDeviceList(tinyDevices);
		masterJson.setDevice(templateJson);
		masterJson.setPastDevice(pastDevice);
		masterJson.setListDevice(listDevice);
		masterJson.setCurrentDevice(currentDeviceList);
		return masterJson;

	}

	/**
	 * Push patient data to kafka
	 *
	 * @param patient data
	 */
	public void pushDataKafka(Object pd) {
		try {
			String data = mapper.writeValueAsString(pd);
			// System.out.println(data);
			pushData.push(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public MonitorJSON getPatientDummyData(String param) {
		// return testDAO.getDummyData(param);
		MonitorJSON mjson = new MonitorJSON();
		mjson.setTime("Wed Feb 01 14:55:40 IST 2017");
		if (param.equalsIgnoreCase("MDC_ECG_LEAD_II")) {
			mjson.setValue(
					"0.09,-0.255,-0.32,0.02,0.295,0.17,-0.165,-0.24,0.085,0.34,0.21,-0.15,-0.245,0.07,0.33,0.195,-0.18,-0.29,0.025,0.28,0.14,-0.245,-0.365,-0.055,0.21,0.08,-0.285,-0.385,-0.065,0.21,0.08,-0.285,-0.39,-0.075,0.185,0.065,-0.29,-0.39,-0.065,0.23,0.125,-0.215,-0.285,0.07,0.365,0.275,-0.055,-0.195,0.04,0.255,0.115,-0.26,-0.37,-0.055,0.21,0.085,-0.27,-0.385,-0.075,0.2,0.07,-0.4,-0.67,-0.4,-7.1054274E-15,0.32,0.545,0.645,0.805,0.755,0.34,-0.21,-0.375,-0.07,0.21,0.1,-0.26,-0.37,-0.055,0.235,0.135,-0.225,-0.355,-0.045,0.25,0.15,-0.205,-0.325,-0.005,0.285,0.19,-0.15,-0.255,0.08,0.4,0.32,-0.04,-0.165,0.165,0.465,0.345,-0.03,-0.16,0.15,0.455,0.33,-0.07,-0.22,0.08,0.365,0.245,-0.14,-0.315,-0.035,0.255,0.155,-0.205,-0.35,-0.065,0.225,0.13,-0.225,-0.365,-0.08,0.2,0.11,-0.245,-0.375,-0.07,0.255,0.225,-0.055,-0.17,0.1,0.415,0.355,-0.055,-0.275,-0.045,0.215,0.12,-0.25,-0.41,-0.12,0.19,0.105,-0.265,-0.435,-0.155,0.115,-0.095,-0.585,-0.755,-0.305,0.465,0.895,0.7,0.395,0.36,0.375,0.135,-0.245,-0.405,-0.13,0.17,0.105,-0.24,-0.4,-0.115,0.21,0.15,-0.21,-0.37,-0.09,0.22,0.185,-0.14,-0.325,-0.065,0.245,0.19,-0.135,-0.295,-0.01,0.315,0.245,-0.09,-0.215,0.065,0.375,0.315,-0.06,-0.245,0.03,0.335,0.265,-0.105,-0.29,-0.035,0.275,0.23,-0.135,-0.345,-0.095,0.21,0.165,-0.195,-0.38,-0.13,0.16,0.11,-0.22,-0.405,-0.165,0.135,0.09,-0.24,-0.4,-0.12,0.195,0.16,-0.175,-0.34,-0.02,0.315,0.225,-0.18,-0.405,-0.16,0.165,0.1,-0.265,-0.445,-0.17,0.15,0.095,-0.27,-0.46,-0.25,-0.1,-0.24,-0.555,-0.49,0.305,1.025,1.055,0.505,-0.01,-7.1054274E-15,0.195,0.105,-0.26,-0.475,-0.215,0.13,0.12,-0.225,-0.445,-0.2,0.135,0.12,-0.22,-0.42,-0.155,0.19,0.165,-0.18,-0.375,-0.11,0.23,0.215,-0.11,-0.295,-0.02,0.32,0.29,-0.06,-0.275,-0.025,0.31,0.285,-0.06,-0.28,-0.05,0.265,0.23,-0.135,-0.38,-0.155,0.165,0.135,-0.225,-0.45,-0.215,0.115,0.09,-0.255,-0.47,-0.24,0.09,0.065,-0.28,-0.49,-0.24,0.11,0.115,-0.205,-0.39,-0.11,0.25,0.265,-0.07,-0.34,-0.165,0.135,0.1,-0.255,-0.48,-0.24,0.1,0.09,-0.25,-0.475,-0.235,0.11,0.075,-0.415,-0.765,-0.505,0.095,0.665,0.815,0.635,0.585,0.54,0.275,-0.16,-0.405,-0.175,0.18,0.185,-0.14,-0.375,-0.17,0.165,0.185,-0.145,-0.375,-0.14,0.22,0.23,-0.085,-0.315,-0.085,0.27,0.28,-0.06,-0.3,-0.045,0.335,0.37,0.05,-0.17,0.035,0.37,0.375,0.025,-0.21,0.005,0.325,0.33,-0.005,-0.26,-0.065,0.255,0.235,-0.135,-0.395,-0.18,0.165,0.17,-0.155,-0.41,-0.215,0.145,0.15,-0.185,-0.46,-0.285,0.075,0.095,-0.24,-0.48,-0.255,0.115,0.175,-0.105,-0.335,-0.095,0.285,0.26,-0.145,-0.445,-0.25,0.1,0.12,-0.205,-0.475,-0.285,0.075,0.115,-0.21,-0.47,-0.28,-0.03,-0.17,-0.535,-0.68,-0.075,0.82,1.08,0.705,0.135,0.015,0.17,0.135,-0.21,-0.48,-0.27,0.095,0.125,-0.205,-0.465,-0.26,0.115,0.16,-0.175,-0.445,-0.25,0.115,0.17,-0.135,-0.385,-0.185,0.18,0.24,-0.065,-0.335,-0.145,0.23,0.29,-0.02,-0.29,-0.115,0.245,0.285,-0.04,-0.32,-0.145,0.215,0.255,-0.075,-0.375,-0.225,0.12,0.155,-0.18,-0.47,-0.295,0.065,0.105,-0.22,-0.505,-0.335,0.035,0.085,-0.24,-0.525,-0.355,0.025,0.12,-0.16,-0.39,-0.175,0.205,0.265,-0.025,-0.32,-0.21,0.1,0.14,-0.175,-0.47,-0.32,0.055,0.145,-0.125,-0.425,-0.29,0.06");
		} else if (param.equalsIgnoreCase("MDC_PRESS_BLD_ART_ABP")) {
			mjson.setValue("");
		} else if (param.equalsIgnoreCase("MDC_PULS_OXIM_PLETH")) {
			mjson.setValue(
					"1967.0,1941.0,1919.0,1900.0,1879.0,1851.0,1815.0,1777.0,1736.0,1696.0,1648.0,1600.0,1599.0,1705.0,1857.0,1972.0,2081.0,2139.0,2185.0,2194.0,2160.0,2108.0,2051.0,1997.0,1958.0,1923.0,1885.0,1851.0,1815.0,1784.0,1760.0,1734.0,1705.0,1671.0,1628.0,1601.0,1627.0,1703.0,1831.0,1974.0,2096.0,2194.0,2265.0,2310.0,2321.0,2306.0,2263.0,2212.0,2165.0,2126.0,2092.0,2059.0,2024.0,1990.0,1948.0,1903.0,1844.0,1780.0,1716.0,1656.0,1607.0,1588.0,1623.0,1715.0,1838.0,1954.0,2041.0,2110.0,2157.0,2186.0,2188.0,2170.0,2139.0,2106.0,2079.0,2054.0,2029.0,1997.0,1961.0,1924.0,1890.0,1859.0,1826.0,1795.0,1769.0,1737.0,1693.0,1667.0,1689.0,1770.0,1895.0,2030.0,2140.0,2217.0,2274.0,2317.0,2333.0,2323.0,2280.0,2226.0,2176.0,2138.0,2106.0,2068.0,2022.0,1972.0,1923.0,1881.0,1834.0,1791.0,1744.0,1695.0,1659.0,1659.0,1722.0,1832.0,1955.0,2067.0,2152.0,2220.0,2268.0,2294.0,2281.0,2249.0,2203.0");
		}
		return mjson;
	}

	// public void storePatientData(Numeric data, int domainId, SampleArray
	// sampleData) {
	// String patientId = null;
	// // should search for the domain id in the static map
	// // if not found add the entry and query new mapping (patient id, domain
	// // id)
	// patientId =
	// MappingConstants.patientDomainMap.get(String.valueOf(domainId));
	// if (BasicUtils.isEmpty(patientId)) {
	// // query to fetch the patient id with the domain id.
	// String query = "SELECT uhid FROM bed_device_history WHERE domainid='" +
	// domainId + "'";
	// List<String> result = patientDao.getListFromNativeQuery(query);
	// if (!BasicUtils.isEmpty(result)) {
	// patientId = result.get(0).toString();
	// MappingConstants.patientDomainMap.put(String.valueOf(domainId),
	// patientId);
	// }
	// }
	//
	// if (!BasicUtils.isEmpty(patientId) && !BasicUtils.isEmpty(domainId)) {
	// patientDataExecutor.execute(new TempPatientData(data, domainId,
	// patientId, sampleData));
	// }
	// }

	@Override
	public MonitorJSON getStoredDeviceData(String patientId, String parameter) {
		MonitorJSON json = new MonitorJSON();
		if (!BasicUtils.isEmpty(patientId)) {
			HashMap<String, String> valueMap = MappingConstants.tempPatientDataMap.get(patientId);
			if (!BasicUtils.isEmpty(valueMap)) {
				json.setTime("");
				json.setValue(valueMap.get(parameter));
			}
		}
		return json;
	}

	@Override
	public List<PatientDataJsonObject> getStoredDeviceNumericData(String patientId) {
		List<PatientDataJsonObject> json = new ArrayList<>();
		HashMap<String, String> valueMap = MappingConstants.tempPatientDataMap.get(patientId);
		if (!BasicUtils.isEmpty(valueMap)) {
			Set<String> listMetricId = MappingConstants.numericMetricMap.keySet();
			for (String metricID : listMetricId) {
				if (valueMap.containsKey(metricID)) {
					PatientDataJsonObject pObj = new PatientDataJsonObject();
					pObj.setParameter(MappingConstants.numericMetricMap.get(metricID));
					pObj.setValue(valueMap.get(metricID));
					json.add(pObj);
				}
			}
		}
		return json;
	}

	@Override
	public List<PatientDataJsonObject> getDummyVariableData() {
		// return testDAO.getDummyVariableData();
		List<PatientDataJsonObject> lOPb = new ArrayList<PatientDataJsonObject>();
		PatientDataJsonObject pObj = new PatientDataJsonObject();
		pObj.setParameter("SYS");
		pObj.setValue("88.0");

		lOPb.add(pObj);

		PatientDataJsonObject pObj1 = new PatientDataJsonObject();
		pObj1.setParameter("DIA");
		pObj1.setValue("75.0");

		lOPb.add(pObj1);

		PatientDataJsonObject pObj2 = new PatientDataJsonObject();
		pObj2.setParameter("HEART RATE");
		pObj2.setValue("212.0");

		lOPb.add(pObj2);

		PatientDataJsonObject pObj3 = new PatientDataJsonObject();
		pObj3.setParameter("ECG RESP RATE");
		pObj3.setValue("78.0");

		lOPb.add(pObj3);

		PatientDataJsonObject pObj4 = new PatientDataJsonObject();
		pObj4.setParameter("PULSE");
		pObj4.setValue("167");

		lOPb.add(pObj4);

		PatientDataJsonObject pObj5 = new PatientDataJsonObject();
		pObj5.setParameter("SPO2");
		pObj5.setValue("78.0");

		lOPb.add(pObj5);

		PatientDataJsonObject pObj6 = new PatientDataJsonObject();
		pObj6.setParameter("BP MEAN");
		pObj6.setValue("78.0");

		lOPb.add(pObj6);

		return lOPb;

	}

	@Override
	public List<PatientDataJsonObject> getDummyVentilatorData() {
		return deviceDAO.getDummyVentilatorData();
	}

	@Override
	public List<PatientDataJsonObject> showDummyVentilatorData() {
		// fetch data from the static map
		List<PatientDataJsonObject> json = new ArrayList<>();
		if (!BasicUtils.isEmpty(MappingConstants.patientVentilatorTempData)) {
			// fetch random value from the list.
			Random r = new Random();
			int lowerBound = 01;
			int upperBound = MappingConstants.patientVentilatorTempData.size();
			int random = r.nextInt(upperBound - lowerBound) + lowerBound;
			VentilatorData vent = null;
			while (BasicUtils.isEmpty(vent)) {
				vent = MappingConstants.patientVentilatorTempData.get(random);
			}

			// return json
			String data = vent.getData();
			JsonParser parser = new JsonParser();
			JsonObject jsonObj = (JsonObject) parser.parse(data);

			if (!BasicUtils.isEmpty(jsonObj)) {
				for (String param : MappingConstants.ventilatorParams) {

					if (jsonObj.has(param)) {
						PatientDataJsonObject pObj = new PatientDataJsonObject();
						pObj.setParameter(param);
						if (param.equalsIgnoreCase("OcclusionPressure")) {
							// enter only numeric data.
							String numStr = jsonObj.get(param).toString();
							numStr = numStr.replaceAll("[A-Z]*[a-z]*", "");
							numStr = numStr.replaceAll("\"", "");
							pObj.setValue(numStr);
						} else {
							String val = jsonObj.get(param).toString();
							val = val.replaceAll("\"", "");
							pObj.setValue(val);
						}
						json.add(pObj);
					} else {
						PatientDataJsonObject pObj = new PatientDataJsonObject();
						pObj.setParameter(param);
						pObj.setValue("--");
						json.add(pObj);
					}
				}
			}

		}
		return json;
	}

	@Override
	public MonitorDummyDataJson getAllMonitorData() {
		MonitorDummyDataJson json = new MonitorDummyDataJson();
		List<PatientDataJsonObject> numericVariable = null;
		MonitorJSON ecgSample = null;
		MonitorJSON pulseSample = null;
		while (BasicUtils.isEmpty(numericVariable) || BasicUtils.isEmpty(ecgSample)
				|| BasicUtils.isEmpty(pulseSample)) {
			Random r = new Random();
			int lowerBound = 01;
			int upperBound = 59;
			Integer randomMinute = r.nextInt(upperBound - lowerBound) + lowerBound;
			Integer randomSecond = r.nextInt(upperBound - lowerBound) + lowerBound;
			numericVariable = deviceDAO.getDummyVariableData(randomMinute, randomSecond);
			ecgSample = deviceDAO.getPatientDummyData("MDC_ECG_LEAD_II", randomMinute, randomSecond);
			pulseSample = deviceDAO.getPatientDummyData("MDC_PULS_OXIM_PLETH", randomMinute, randomSecond);
		}

		for (PatientDataJsonObject pObj : numericVariable) {
			String param = pObj.getParameter();
			String value = pObj.getValue();
			if (param.equalsIgnoreCase("SYS")) {
				json.setSys(value);
			} else if (param.equalsIgnoreCase("DIA")) {
				json.setDia(value);
			} else if (param.equalsIgnoreCase("HEART RATE")) {
				json.setHeartRate(value);
			} else if (param.equalsIgnoreCase("ECG RESP RATE")) {
				json.setRespRate(value);
			} else if (param.equalsIgnoreCase("CO2 RESP RATE")) {
				json.setRespRate(value);
			} else if (param.equalsIgnoreCase("ETCO2")) {

			} else if (param.equalsIgnoreCase("PULSE")) {
				json.setPulse(value);
			} else if (param.equalsIgnoreCase("SPO2")) {
				json.setSp02(value);
			} else if (param.equalsIgnoreCase("BP MEAN")) {
				json.setBpMean(value);
			}
		}

		json.setEcgSample(ecgSample.getValue());
		json.setTime(ecgSample.getTime());
		json.setPulseSample(pulseSample.getValue());

		return json;
	}

	@Override
	public void dataMigrationService() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(60000);
						pushDataToPostgres();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		t.start();

		// pushDataToPostgres();
	}

	protected void pushDataToPostgres() {
		// get valid uhid's
		String timeBefore;
		String timeAfter;

		Date currDate = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		timeAfter = (sdf.format(currDate)) + "+0000";

		Date oldDate = new Date(currDate.getTime() - 60000);
		timeBefore = (sdf.format(oldDate)) + "+0000";

		System.out.println("time after :" + timeAfter);
		System.out.println("time before :" + timeBefore);

		String getUhid = "SELECT uhid FROM dashboard_finalview WHERE uhid!='null'";
		List<String> uhidList = deviceDAO.executeNativeQuery(getUhid);
		
		String isCarescapeACK = BasicConstants.props.getProperty(BasicConstants.PROP_CARESCAPE_ACK_LISTENER);
		if (isCarescapeACK != null && !isCarescapeACK.isEmpty()) {
	      if (isCarescapeACK.equalsIgnoreCase("true")) {
	        pushDataCarescapeACKGateway(timeBefore, timeAfter, true);
	      }
	    }

		// find device integration type.

		HashMap<String, String> integrationTypeMap = userPanelService.getAddSettingPreference();
		if (!BasicUtils.isEmpty(integrationTypeMap)) {
			String integrationType = integrationTypeMap.get("integration_type");
			if (!BasicUtils.isEmpty(integrationType)) {
				if (integrationType.equalsIgnoreCase("infinity")) {

					pushDataInfinityGateway(uhidList, timeBefore, timeAfter);

				} else if (integrationType.equalsIgnoreCase("inicu")) {

					pushDataInicuGateway(uhidList, timeBefore, timeAfter, true);
				}
			}
		}

	}

	private void pushDataCarescapeACKGateway(String timeBefore, String timeAfter, boolean isNew) {

	    String devType = "carescapeACK";
	    String query = "select * from inicu.patient_devicedata_carescapeack where " + " date >= '" + timeBefore
	        + "'  and date < '" + timeAfter + "' ALLOW FILTERING";

	    // sort and segregate device data based on device type.
	    HashMap<String, List<String>> deviceDetail = new HashMap<>();
	    HashMap<String, List<String>> deviceMonitorTimestamp = new HashMap<>();

	    List<DeviceDataCarescapeACK> resultSet = cassandraTemplate.select(query, DeviceDataCarescapeACK.class);
//	    System.out.println("the query is : " + query);
	    System.out.println("Result Set = " + resultSet.toString());
	    if (!BasicUtils.isEmpty(resultSet)) {
	      for (DeviceDataCarescapeACK pd : resultSet) {
	        if (!BasicUtils.isEmpty(pd.getPatientId())) {
	          if (deviceDetail.containsKey(devType)) {

	            List<String> listData = deviceDetail.get(devType);
	            List<String> listDeviceMonitorTimestamp = deviceMonitorTimestamp.get(devType);
	            if (!BasicUtils.isEmpty(listData)) {
	              // For Data
	              listData.add(pd.getData());
	              deviceDetail.put(devType, listData);

	              // For Server Time
	              listDeviceMonitorTimestamp.add(pd.getTimestamp());
	              deviceMonitorTimestamp.put(devType, listDeviceMonitorTimestamp);
	            }
	          } else {

	            // For Data
	            List<String> listData = new ArrayList<>();
	            listData.add(pd.getData());
	            deviceDetail.put(devType, listData);

	            // For Server Time
	            List<String> listdeviceMonitorTimestamp = new ArrayList<>();
	            listdeviceMonitorTimestamp.add(pd.getTimestamp());
	            deviceMonitorTimestamp.put(devType, listdeviceMonitorTimestamp);
	          }

	        }
	        System.out.println("inside pushdatacarescapeack ");
	        SaveCarescapeACkDataPostgres saveCarescapeACkDataPostgres = new SaveCarescapeACkDataPostgres(
	            deviceDetail.get(devType), deviceMonitorTimestamp.get(devType), pd.getPatientId(),
	            pd.getBedId());
	        saveCarescapeACkDataPostgres.parse();
	        deviceDetail.clear();
	        deviceMonitorTimestamp.clear();
	      }
	    }
	  }
	
	private void pushDataInfinityGateway(List<String> uhidList, String timeBefore, String timeAfter) {

		String[] paramArr = {"PLS", "SpO2", "HR", "ART M", "ART S", "ART D", "Ta", "NBP D", "NBP S", "NBP M", "RESP",
				"", "PR", "SPO2", "RR"};
		try {
			// timeBefore = "2017-03-01 19:06:00";
			// timeAfter = "2017-03-01 19:06:59";
			if (!BasicUtils.isEmpty(uhidList)) {

				for (String uhid : uhidList) {
					// iterate through uhid to get the data.
					String query = "select * from inicu.patient_devicedata_infinity where uhid = '" + uhid.trim() + "'"
							+ "AND device_time >= '" + timeBefore + "'  and device_time <= '" + timeAfter + "'";

					System.out.println("query fetch cassandra *** " + query);
					List<DeviceDataInfinity> resultSet = cassandraTemplate.select(query, DeviceDataInfinity.class);
					if (!BasicUtils.isEmpty(resultSet)) {
						// iterate over the result set to generate mean values.
						for (DeviceDataInfinity dev : resultSet) {

							String value = dev.getValue();
							JSONObject jsonObj = new JSONObject(value);
							if (!BasicUtils.isEmpty(jsonObj)) {
								for (String param : paramArr) {
									if (jsonObj.has(param)) {
										if (!BasicUtils.isEmpty(jsonObj.get(param))) {
											pushValueToMeanMap(jsonObj.get(param).toString(), param, uhid);
										}
									}
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (HL7Constants.meanMap != null) {

			/*
			 * System.out.println("mean map"); for(String
			 * key:HL7Constants.meanMap.keySet()){ System.out.println("UHID:"+key);
			 * HashMap<String, List<Float>> map = HL7Constants.meanMap.get(key); for(String
			 * param:map.keySet()){ System.out.println("PARAM:"+param); List<Float> values =
			 * map.get(param); for(Float f:values){ System.out.println(f); } } }
			 */

			// iterate through existing uhid list to generate map of mean
			// values.

			// if(!BasicUtils.isEmpty(uhidList)){
			// for(String uhid:uhidList){
			// if(!BasicUtils.isEmpty(HL7Constants.meanMap.get(uhid))){
			//
			// HashMap<String, Float> meanvalueMap = calculateMean(uhid,
			// HL7Constants.meanMap.get(uhid));
			// if(!BasicUtils.isEmpty(meanvalueMap)){
			// insertDeviceDataPostgres(meanvalueMap,uhid);
			// }
			// }
			// }
			// }

			if (!BasicUtils.isEmpty(HL7Constants.meanMap)) {
				// for(String uhid:HL7Constants.meanMap.keySet()){
				// HashMap<String, Float> meanvalueMap = calculateMean(uhid,
				// HL7Constants.meanMap.get(uhid));
				// if(!BasicUtils.isEmpty(meanvalueMap)){
				// insertDeviceDataPostgres(meanvalueMap,uhid);
				// // remove the uhid from the map once pushed.
				// HL7Constants.meanMap.remove(uhid);
				// }
				// }

				Iterator it = HL7Constants.meanMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					String uhid = (String) pair.getKey();
					HashMap<String, Float> meanvalueMap = calculateMean(uhid, HL7Constants.meanMap.get(uhid));
					if (!BasicUtils.isEmpty(meanvalueMap)) {
						insertDeviceDataPostgres(meanvalueMap, uhid);
						System.out.println("data pushed from cassandra to postgres");
						it.remove(); // avoids ConcurrentModificationException
					}
				}
			}
		}
	}

	/**
	 * This method inserts the fitted model into database so that it can be used for
	 * realtime calculation of PhysiScore
	 *
	 * @param meanValueMap
	 * @param uhid
	 */
	public void insertRegressionModelIntoDatabase(FittingResult fittingResult) throws Exception {
		patientDao.saveObject(fittingResult);
	}

	/**
	 * This method inserts the classifier weights into database so that it can be
	 * used for realtime calculation of PhysiScore
	 *
	 * @param meanValueMap
	 * @param uhid
	 */
	public void insertClassifierWeightsIntoDatabase(ClassifierWeights classifierWeights) throws Exception {
		patientDao.saveObject(classifierWeights);
	}

	private void insertDeviceDataPostgres(HashMap<String, Float> meanValueMap, String uhid) {

		// push meanvaluemap into db
		if (!BasicUtils.isEmpty(meanValueMap)) {
			DeviceMonitorDetail dev = new DeviceMonitorDetail();
			int counter = 0;

			if (!BasicUtils.isEmpty(meanValueMap.get("RESP"))) {
				dev.setCo2Resprate(String.valueOf(meanValueMap.get("RESP")));
				counter = counter + 1;
			}
			// if(!BasicUtils.isEmpty(meanValueMap.get(""))){
			// dev.setBeddeviceid(String.valueOf(meanValueMap.get("")));
			// }

			// if(!BasicUtils.isEmpty(meanValueMap.get(""))){
			// dev.setCo2Resprate(String.valueOf(meanValueMap.get("")));
			// }

			if (!BasicUtils.isEmpty(meanValueMap.get("ART D"))) {
				dev.setDiaBp(String.valueOf(meanValueMap.get("ART D")));
				counter = counter + 1;
			}

			// if(!BasicUtils.isEmpty(meanValueMap.get(""))){
			// dev.setEcgResprate(String.valueOf(meanValueMap.get("")));
			// }

			// if(!BasicUtils.isEmpty(meanValueMap.get(""))){
			// dev.setEtco2(String.valueOf(meanValueMap.get("")));
			// }

			if (!BasicUtils.isEmpty(meanValueMap.get("ART M"))) {
				dev.setMeanBp(String.valueOf(meanValueMap.get("ART M")));
				counter = counter + 1;
			}

			if (!BasicUtils.isEmpty(meanValueMap.get("PR"))) {
				dev.setPulserate(String.valueOf(meanValueMap.get("PR")));
				dev.setHeartrate(String.valueOf(meanValueMap.get("PR")));
				counter = counter + 1;
			}

			if (!BasicUtils.isEmpty(meanValueMap.get("HR"))) {
				dev.setHeartrate(String.valueOf(meanValueMap.get("HR")));
				counter = counter + 1;
			}

			if (!BasicUtils.isEmpty(meanValueMap.get("PLS"))) {
				dev.setPulserate(String.valueOf(meanValueMap.get("PLS")));
				counter = counter + 1;
			}

			if (!BasicUtils.isEmpty(meanValueMap.get("SpO2"))) {
				dev.setSpo2(String.valueOf(meanValueMap.get("SpO2")));
				counter = counter + 1;
			}

			if (!BasicUtils.isEmpty(meanValueMap.get("SPO2"))) {
				dev.setSpo2(String.valueOf(meanValueMap.get("SPO2")));
				counter = counter + 1;
			}

			if (!BasicUtils.isEmpty(meanValueMap.get("PM"))) {
				dev.setHeartrate(String.valueOf(meanValueMap.get("PM")));
				counter = counter + 1;
			}

			if (!BasicUtils.isEmpty(meanValueMap.get("BPM"))) {
				dev.setHeartrate(String.valueOf(meanValueMap.get("BPM")));
				counter = counter + 1;
			}

			if (!BasicUtils.isEmpty(meanValueMap.get("ART S"))) {
				dev.setSysBp(String.valueOf(meanValueMap.get("ART S")));
				counter = counter + 1;
			}

			if (!BasicUtils.isEmpty(meanValueMap.get("NBP D"))) {
				dev.setNbp_d(String.valueOf(meanValueMap.get("NBP D")));
				counter = counter + 1;
			}

			if (!BasicUtils.isEmpty(meanValueMap.get("NBP S"))) {
				dev.setNbp_s(String.valueOf(meanValueMap.get("NBP S")));
				counter = counter + 1;
			}

			if (!BasicUtils.isEmpty(meanValueMap.get("NBP M"))) {
				dev.setNbp_m(String.valueOf(meanValueMap.get("NBP M")));
				counter = counter + 1;
			}

			if (!BasicUtils.isEmpty(uhid)) {
				dev.setUhid(uhid);
			}

			if (!BasicUtils.isEmpty(meanValueMap.get("RR"))) {
				dev.setEcgResprate(String.valueOf(meanValueMap.get("RR")));
				counter = counter + 1;
			}

			dev.setStarttime(new Timestamp(new Date().getTime()));

			try {
				if (counter > 0) {
					patientDao.saveObject(dev);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void pushValueToMeanMap(String paramValue, String parameter, String uhid) {
		if (!BasicUtils.isEmpty(paramValue) && paramValue.trim().matches(BasicConstants.REG_EX_NUMBER_PATTERN)) {
			if (!BasicUtils.isEmpty(HL7Constants.meanMap.get(uhid))) {
				HashMap<String, List<Float>> map = HL7Constants.meanMap.get(uhid);
				if (!BasicUtils.isEmpty(map)) {
					if (!BasicUtils.isEmpty(map.get(parameter))) {
						try {
							List<Float> valueList = map.get(parameter);
							valueList.add(Float.parseFloat(paramValue));
							map.put(parameter, valueList);
							HL7Constants.meanMap.put(uhid, map);
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						try {
							HashMap<String, List<Float>> mapVal = new HashMap<>();
							List<Float> value = new ArrayList<>();
							value.add(Float.parseFloat(paramValue));
							map.put(parameter, value);
							HL7Constants.meanMap.put(uhid, map);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				try {
					HashMap<String, List<Float>> mapVal = new HashMap<>();
					List<Float> value = new ArrayList<>();
					value.add(Float.parseFloat(paramValue));
					mapVal.put(parameter, value);
					HL7Constants.meanMap.put(uhid, mapVal);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void pushDataInicuGateway(List<String> uhidList, String timeBefore, String timeAfter, boolean isNew) {

		// timeBefore = "2017-06-08 12:08:00+0530";
		// timeAfter = "2017-06-08 12:09:00+0530";
		if (!BasicUtils.isEmpty(uhidList)) {
			for (String uhid : uhidList) {
				String query = "select * from inicu.patient_devicedata_inicu where uhid = '" + uhid.trim() + "' "
						+ "AND device_time >= '" + timeBefore + "'  and device_time < '" + timeAfter + "'";

				// sort and segregate device data based on device type.
				HashMap<String, List<String>> deviceDetail = new HashMap<>();
				HashMap<String, List<String>> serverTimeDetail = new HashMap<>();

				List<PatientDeviceDataInicu> resultSet = cassandraTemplate.select(query, PatientDeviceDataInicu.class);
//				System.out.println("Result Set = " + resultSet.toString());
				if (!BasicUtils.isEmpty(resultSet)) {
					for (PatientDeviceDataInicu pd : resultSet) {
						if (!BasicUtils.isEmpty(pd.getDeviceType())) {
							String devType = pd.getDeviceType();
							if (deviceDetail.containsKey(devType)) {
								List<String> listData = deviceDetail.get(devType);
								List<String> listServerTime = serverTimeDetail.get(devType);
								if (!BasicUtils.isEmpty(listData)) {
									// For Data
									listData.add(pd.getData());
									deviceDetail.put(devType, listData);

									// For Server Time
									listServerTime.add(pd.getServerTime());
									serverTimeDetail.put(devType, listServerTime);
								}
							} else {

								// For Data
								List<String> listData = new ArrayList<>();
								listData.add(pd.getData());
								deviceDetail.put(devType, listData);

								// For Server Time
								List<String> listServerTime = new ArrayList<>();
								listServerTime.add(pd.getServerTime());
								serverTimeDetail.put(devType, listServerTime);
							}
						}
					}

					// no paramater
					Class noparams[] = {};

					System.out.println("<dev detail>");
					System.out.println(deviceDetail);

					// fetch beddeviceid based on uhid.
					String beddevid = null;
					String fetchbeddevid = "SELECT bedid FROM bed_device_detail WHERE uhid = '" + uhid.trim() + "' "
							+ "AND status = true";

					List<Object> result = patientDao.getListFromNativeQuery(fetchbeddevid);
					if (!BasicUtils.isEmpty(result)) {
						beddevid = result.get(0).toString();
					}

					// iterate over deviceDetail and invoke respective classes
					// and methods.
					for (String key : deviceDetail.keySet()) {
						System.out.println("DEVICE PACKAGE :" + key.trim());
						System.out.println("DEVICE LIST DATA :" + deviceDetail.get(key).toString());
						System.out.println("SERVER TIME LIST :" + serverTimeDetail.get(key).toString());
						String packageName = DeviceObjectMapper.deviceObjectNameMap.get(key.trim().toLowerCase());
						try {
							if (!BasicUtils.isEmpty(uhid) && !BasicUtils.isEmpty(beddevid)) {
								Class<?> cls = Class.forName(packageName);
								Constructor<?> cons = cls.getConstructor(
										new Class[]{List.class, List.class, String.class, String.class});
								Object obj = cons.newInstance(deviceDetail.get(key), serverTimeDetail.get(key), uhid,
										beddevid);
								Method method = obj.getClass().getMethod("parse");
								method.invoke(obj);
							}

						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	private void pushDataInicuGateway(List<String> uhidList, String timeBefore, String timeAfter) {
		// if (!BasicUtils.isEmpty(uhidList)) {
		// for (String s : uhidList) {
		// if(!BasicUtils.isEmpty(s)){
		//
		//
		// // check which all devices are connected to that particular _uhid.
		//
		//
		//
		// System.out.println("uhid is not empty: inside inicu gateway");
		// // timeBefore = "2017-04-21 06:33:00+0530";
		// // timeAfter = "2017-04-21 07:22:00+0530";
		//
		// // String query = "select * from inicu.patient_devicedata_inicu where
		// uhid = '" + s.trim() + "' "
		// // + "AND device_type = 'masimo' " + "AND device_time >= '" +
		// timeBefore
		// // + "' and device_time <= '" + timeAfter + "'";
		//
		// String query = "select * from inicu.patient_devicedata_inicu_copy1
		// where uhid = '" + s.trim() + "' "
		// + "AND device_time >= '" + timeBefore
		// + "' and device_time <= '" + timeAfter + "'";
		//
		// System.out.println(query);
		//
		// List<PatientDeviceDataInicu> resultSet =
		// cassandraTemplate.select(query, PatientDeviceDataInicu.class);
		// if (!BasicUtils.isEmpty(resultSet)) {
		// for (PatientDeviceDataInicu pd : resultSet) {
		// String[] paramArr = { "PLS", "SpO2", "HR", "ART M", "ART S", "ART D",
		// "Ta", "NBP D", "NBP S",
		// "NBP M" };
		// if (pd.getDeviceType().equalsIgnoreCase("masimo")) {
		// paramArr = new String[] { "SPO2", "PM", "PI", "DESAT", "IDELTA",
		// "PVI", "ALARM",
		// "ACSALARM" };
		// // System.out.println("masimo param generated:"+paramArr);
		// } else if (pd.getDeviceType().equalsIgnoreCase("draeger")) {
		// // paramArr = new String[]{};
		// }
		//
		// try {
		//
		// // String data = "{\"NBP M\":\"43\",\"NBP
		// //
		// S\":\"69\",\"RESP\":\"71\",\"PLS\":\"181\",\"SpO2\":\"99\",\"HR\":\"178\",\"NBP
		// // D\":\"31\"}";
		// String value = pd.getData();
		// JSONObject jsonObj = new JSONObject(value);
		// if (!BasicUtils.isEmpty(jsonObj)) {
		// for (String param : paramArr) {
		// if (jsonObj.has(param)) {
		// if (!BasicUtils.isEmpty(jsonObj.get(param))) {
		// pushValueToMeanMap(jsonObj.get(param).toString(), param,
		// pd.getUhid());
		// }
		// }
		// }
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		//
		// if (HL7Constants.meanMap != null) {
		//
		// /*
		// * System.out.println("mean map"); for(String
		// * key:HL7Constants.meanMap.keySet()){
		// * System.out.println("UHID:"+key); HashMap<String,
		// * List<Float>> map = HL7Constants.meanMap.get(key);
		// * for(String param:map.keySet()){
		// * System.out.println("PARAM:"+param); List<Float>
		// * values = map.get(param); for(Float f:values){
		// * System.out.println(f); } } }
		// */
		//
		// // iterate through existing uhid list to generate
		// // map of mean values.
		//
		// // if(!BasicUtils.isEmpty(uhidList)){
		// // for(String uhid:uhidList){
		// // if(!BasicUtils.isEmpty(HL7Constants.meanMap.get(uhid))){
		// //
		// // HashMap<String, Float> meanvalueMap =
		// // calculateMean(uhid,
		// // HL7Constants.meanMap.get(uhid));
		// // if(!BasicUtils.isEmpty(meanvalueMap)){
		// // insertDeviceDataPostgres(meanvalueMap,uhid);
		// // }
		// // }
		// // }
		// // }
		//
		// if (!BasicUtils.isEmpty(HL7Constants.meanMap)) {
		// // for(String
		// // uhid:HL7Constants.meanMap.keySet()){
		// // HashMap<String, Float> meanvalueMap =
		// // calculateMean(uhid,
		// // HL7Constants.meanMap.get(uhid));
		// // if(!BasicUtils.isEmpty(meanvalueMap)){
		// // insertDeviceDataPostgres(meanvalueMap,uhid);
		// // // remove the uhid from the map once pushed.
		// // HL7Constants.meanMap.remove(uhid);
		// // }
		// // }
		//
		// System.out.println("mean map is not empty.");
		// Iterator it = HL7Constants.meanMap.entrySet().iterator();
		// while (it.hasNext()) {
		// Map.Entry pair = (Map.Entry) it.next();
		// String uhid = (String) pair.getKey();
		// HashMap<String, Float> meanvalueMap = calculateMean(uhid,
		// HL7Constants.meanMap.get(uhid));
		// if (!BasicUtils.isEmpty(meanvalueMap)) {
		// insertDeviceDataPostgres(meanvalueMap, uhid);
		// System.out.println("data pushed from cassandra to postgres");
		// it.remove(); // avoids a
		// // ConcurrentModificationException
		// }
		// }
		// }
		// }
		// }
		// }
		// }
		// }

		// flow for nihon kohden for demo server
		// needs to be commented for every other cases.

		String[] paramArr = {"PLS", "SpO2", "HR", "ART M", "ART S", "ART D", "Ta", "NBP D", "NBP S", "NBP M", "RESP",
				"", "PR", "SPO2", "RR"};
		try {
			// timeBefore = "2017-03-01 19:06:00";
			// timeAfter = "2017-03-01 19:06:59";
			if (!BasicUtils.isEmpty(uhidList)) {

				for (String uhid : uhidList) {
					// iterate through uhid to get the data.
					String query = "select * from inicu.patient_devicedata_infinity where uhid = '" + uhid.trim() + "'"
							+ "AND device_time >= '" + timeBefore + "'  and device_time <= '" + timeAfter + "'";

					System.out.println("query fetch cassandra *** " + query);
					List<DeviceDataInfinity> resultSet = cassandraTemplate.select(query, DeviceDataInfinity.class);
					if (!BasicUtils.isEmpty(resultSet)) {
						// iterate over the result set to generate mean values.
						for (DeviceDataInfinity dev : resultSet) {
							// String data = "{\"NBP M\":\"43\",\"NBP
							// S\":\"69\",\"RESP\":\"71\",\"PLS\":\"181\",\"SpO2\":\"99\",\"HR\":\"178\",\"NBP
							// D\":\"31\"}";
							String value = dev.getValue();
							JSONObject jsonObj = new JSONObject(value);
							if (!BasicUtils.isEmpty(jsonObj)) {
								for (String param : paramArr) {
									if (jsonObj.has(param)) {
										if (!BasicUtils.isEmpty(jsonObj.get(param))) {
											pushValueToMeanMap(jsonObj.get(param).toString(), param, uhid);
										}
									}
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (HL7Constants.meanMap != null) {

			/*
			 * System.out.println("mean map"); for(String
			 * key:HL7Constants.meanMap.keySet()){ System.out.println("UHID:"+key);
			 * HashMap<String, List<Float>> map = HL7Constants.meanMap.get(key); for(String
			 * param:map.keySet()){ System.out.println("PARAM:"+param); List<Float> values =
			 * map.get(param); for(Float f:values){ System.out.println(f); } } }
			 */

			// iterate through existing uhid list to generate map of mean
			// values.

			// if(!BasicUtils.isEmpty(uhidList)){
			// for(String uhid:uhidList){
			// if(!BasicUtils.isEmpty(HL7Constants.meanMap.get(uhid))){
			//
			// HashMap<String, Float> meanvalueMap = calculateMean(uhid,
			// HL7Constants.meanMap.get(uhid));
			// if(!BasicUtils.isEmpty(meanvalueMap)){
			// insertDeviceDataPostgres(meanvalueMap,uhid);
			// }
			// }
			// }
			// }

			if (!BasicUtils.isEmpty(HL7Constants.meanMap)) {
				// for(String uhid:HL7Constants.meanMap.keySet()){
				// HashMap<String, Float> meanvalueMap = calculateMean(uhid,
				// HL7Constants.meanMap.get(uhid));
				// if(!BasicUtils.isEmpty(meanvalueMap)){
				// insertDeviceDataPostgres(meanvalueMap,uhid);
				// // remove the uhid from the map once pushed.
				// HL7Constants.meanMap.remove(uhid);
				// }
				// }

				Iterator it = HL7Constants.meanMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					String uhid = (String) pair.getKey();
					HashMap<String, Float> meanvalueMap = calculateMean(uhid, HL7Constants.meanMap.get(uhid));
					if (!BasicUtils.isEmpty(meanvalueMap)) {
						insertDeviceDataPostgres(meanvalueMap, uhid);
						System.out.println("data pushed from cassandra to postgres");
						it.remove(); // avoids a ConcurrentModificationException
					}
				}
			}
		}

	}

	private void pushDataInicuGateway(List<String> uhidList, String timeBefore, String timeAfter, String test) {
		if (!BasicUtils.isEmpty(uhidList)) {
			for (String s : uhidList) {

				// for every iteration calculate vitals
				HashMap<String, List<Float>> vitalMap = new HashMap<String, List<Float>>();
				// event time should be from current time to one minute prior to
				// the current time.

				// for every uhid calculate mean vitals from cassandra
				String query = "select * from inicu.patient_monitor_data where patient_id = '" + s.trim() + "'"
						+ "AND event_time >= '" + timeBefore + "'  and event_time <= '" + timeAfter + "'"
						+ "AND value_type = '" + BasicConstants.VALUE_TYPE_NUMERIC + "'";

				System.out.println("Query:" + query);
				List<PatientMonitorData> resultSet = cassandraTemplate.select(query, PatientMonitorData.class);
				if (!BasicUtils.isEmpty(resultSet)) {
					for (PatientMonitorData pd : resultSet) {
						String metricId = pd.getMetricId();
						if (!BasicUtils.isEmpty(metricId)) {
							// find corresponding numeric map and push it to the
							// hashmap
							String metric = MappingConstants.numericMetricMap.get(metricId.trim());
							if (!BasicUtils.isEmpty(metric)) {
								// check for map and push the values
								if (vitalMap.containsKey(metric)) {
									List<Float> val = vitalMap.get(metric.trim());
									val.add(Float.parseFloat(pd.getValue()));
									vitalMap.put(metric, val);
								} else {
									// add new entry
									List<Float> val = new ArrayList<>();
									val.add(Float.parseFloat(pd.getValue()));
									vitalMap.put(metric, val);
								}
							}
						}
					}
				}
				// move the calculated vitals to postgres
				HashMap<String, Float> meanValMap = calculateMean(s, vitalMap);
				if (!BasicUtils.isEmpty(meanValMap)) {
					// push the data to postgres.
					pushMeanToPostgres(s, meanValMap);
				}

				// migrate ventilator mean value data.

				// find deviceId mapped with uhid.
				String fetchDevId = "SELECT deviceserialno FROM bed_device_history " + "WHERE uhid='" + s.trim() + "' "
						+ "AND devicetype='ventilator' AND available='TRUE'";
				List<Object> result = patientDao.getListFromNativeQuery(fetchDevId);
				if (!BasicUtils.isEmpty(result)) {
					String deviceId = result.get(0).toString();
					if (!BasicUtils.isEmpty(deviceId)) {
						HashMap<String, List<Float>> ventMap = new HashMap<>();
						// query cassandra to fetch ventilator data.
						// String getVentData = "select * FROM
						// inicu.patient_ventilator_data where device_id =
						// '"+deviceId.trim()+"'"
						// + " AND device_time >= '"+timeBefore+"' and
						// device_time <= '"+timeAfter+"'";

						String getVentData = "select * FROM inicu.patient_ventilator_data where device_id = 'dev123456789' AND device_time >= '2017-02-02 12:12:11'  and device_time <= '2017-02-02 12:12:15'";

						List<PatientVentilatorData> listVent = cassandraTemplate.select(getVentData,
								PatientVentilatorData.class);
						if (!BasicUtils.isEmpty(listVent)) {
							for (PatientVentilatorData pVd : listVent) {
								ventMap = storeVentDataInMap(pVd, ventMap);
							}
						}

						if (!BasicUtils.isEmpty(ventMap)) {
							// push the vent map to the postgres
							HashMap<String, Float> meanMap = calculateMean(s, ventMap);
							if (!BasicUtils.isEmpty(meanMap)) {
								pushVentMeanPostgres(s, meanMap);
							}
						}
					}
				}
			}
		}
	}

	private void pushVentMeanPostgres(String s, HashMap<String, Float> meanMap) {
		DeviceVentilatorData devVent = new DeviceVentilatorData();

		devVent.setUhid(s);

		// fetch bed id from uhid
		String query = "SELECT bedid FROM bed_device_history " + "WHERE uhid='" + s.trim() + "' "
				+ "AND devicetype='ventilator' AND available='TRUE'";

		List<Object> result = patientDao.getListFromNativeQuery(query);
		if (!BasicUtils.isEmpty(result)) {
			String bedId = result.get(0).toString();
			if (!BasicUtils.isEmpty(bedId)) {
				devVent.setBeddeviceid(bedId);
			}
		}

		if (!BasicUtils.isEmpty(meanMap.get("fi02"))) {
			devVent.setFio2(meanMap.get("fi02").toString());
		}

		if (!BasicUtils.isEmpty(meanMap.get("meanbp"))) {
			devVent.setMeanbp(meanMap.get("meanbp").toString());
		}

		if (!BasicUtils.isEmpty(meanMap.get("minairwaypress"))) {
			devVent.setMap(meanMap.get("minairwaypress").toString());
		}

		if (!BasicUtils.isEmpty(meanMap.get("occpressure"))) {
			devVent.setOccpressure(meanMap.get("occpressure").toString());
		}

		if (!BasicUtils.isEmpty(meanMap.get("peakbp"))) {
			devVent.setPeakbp(meanMap.get("peakbp").toString());
		}

		if (!BasicUtils.isEmpty(meanMap.get("peepbp"))) {
			devVent.setPeep(meanMap.get("peepbp").toString());
		}

		if (!BasicUtils.isEmpty(meanMap.get("spontrr"))) {
			devVent.setFlowpermin(meanMap.get("spontrr").toString());
		}
		try {
			patientDao.saveObject(devVent);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private HashMap<String, List<Float>> storeVentDataInMap(PatientVentilatorData pVd,
															HashMap<String, List<Float>> ventMap) {

		if (!BasicUtils.isEmpty(pVd.getFio2())) {
			if (!BasicUtils.isEmpty(ventMap.get("fi02"))) {
				List<Float> fio2list = ventMap.get("fi02");
				fio2list.add(Float.parseFloat(pVd.getFio2().toString()));
				ventMap.put("fi02", fio2list);
			} else {
				List<Float> fio2List = new ArrayList<>();
				fio2List.add(Float.parseFloat(pVd.getFio2().toString()));
				ventMap.put("fi02", fio2List);

			}
		}

		if (!BasicUtils.isEmpty(pVd.getMeanBp())) {
			if (!BasicUtils.isEmpty(ventMap.get("meanbp"))) {
				List<Float> meanBplist = ventMap.get("meanbp");
				meanBplist.add(Float.parseFloat(pVd.getMeanBp().toString()));
				ventMap.put("meanbp", meanBplist);
			} else {
				List<Float> meanBplist = new ArrayList<>();
				meanBplist.add(Float.parseFloat(pVd.getMeanBp().toString()));
				ventMap.put("meanbp", meanBplist);
			}
		}

		if (!BasicUtils.isEmpty(pVd.getMinAirwayPress())) {
			if (!BasicUtils.isEmpty(ventMap.get("minairwaypress"))) {
				List<Float> list = ventMap.get("minairwaypress");
				list.add(Float.parseFloat(pVd.getMinAirwayPress().toString()));
				ventMap.put("minairwaypress", list);
			} else {
				List<Float> list = new ArrayList<>();
				list.add(Float.parseFloat(pVd.getMinAirwayPress().toString()));
				ventMap.put("minairwaypress", list);

			}
		}

		if (!BasicUtils.isEmpty(pVd.getOccPressure())) {
			if (!BasicUtils.isEmpty(ventMap.get("occpressure"))) {
				List<Float> list = ventMap.get("occpressure");
				list.add(Float.parseFloat(pVd.getOccPressure().toString()));
				ventMap.put("occpressure", list);
			} else {
				List<Float> list = new ArrayList<>();
				list.add(Float.parseFloat(pVd.getOccPressure().toString()));
				ventMap.put("occpressure", list);

			}
		}

		if (!BasicUtils.isEmpty(pVd.getPeakBp())) {
			if (!BasicUtils.isEmpty(ventMap.get("peakbp"))) {
				List<Float> list = ventMap.get("peakbp");
				list.add(Float.parseFloat(pVd.getPeakBp().toString()));
				ventMap.put("peakbp", list);
			} else {
				List<Float> list = new ArrayList<>();
				list.add(Float.parseFloat(pVd.getPeakBp().toString()));
				ventMap.put("peakbp", list);
			}
		}

		if (!BasicUtils.isEmpty(pVd.getPeepBp())) {
			if (!BasicUtils.isEmpty(ventMap.get("peepbp"))) {
				List<Float> list = ventMap.get("peepbp");
				list.add(Float.parseFloat(pVd.getPeepBp().toString()));
				ventMap.put("peepbp", list);
			} else {
				List<Float> list = new ArrayList<>();
				list.add(Float.parseFloat(pVd.getPeepBp().toString()));
				ventMap.put("peepbp", list);
			}
		}

		if (!BasicUtils.isEmpty(pVd.getPlatPressure())) {
			if (!BasicUtils.isEmpty(ventMap.get("platpressure"))) {
				List<Float> list = ventMap.get("platpressure");
				list.add(Float.parseFloat(pVd.getPlatPressure().toString()));
				ventMap.put("platpressure", list);
			} else {
				List<Float> list = new ArrayList<>();
				list.add(Float.parseFloat(pVd.getPlatPressure().toString()));
				ventMap.put("platpressure", list);

			}
		}

		if (!BasicUtils.isEmpty(pVd.getSpontRR())) {
			if (!BasicUtils.isEmpty(ventMap.get("spontrr"))) {
				List<Float> list = ventMap.get("spontrr");
				list.add(Float.parseFloat(pVd.getSpontRR().toString()));
				ventMap.put("spontrr", list);
			} else {
				List<Float> list = new ArrayList<>();
				list.add(Float.parseFloat(pVd.getSpontRR().toString()));
				ventMap.put("spontrr", list);
			}
		}

		return ventMap;
	}

	private void pushMeanToPostgres(String s, HashMap<String, Float> meanValMap) {
		DeviceMonitorDetail dev = new DeviceMonitorDetail();

		dev.setUhid(s);

		// fetch bed device id and device monitor id
		String query = "SELECT bedid, uhid FROM bed_device_history " + "WHERE uhid='" + s.trim() + "' "
				+ "AND devicetype='monitor' AND available='TRUE'";

		List<Object[]> resultSet = patientDao.getListFromNativeQuery(query);
		if (!BasicUtils.isEmpty(resultSet)) {
			Object[] result = resultSet.get(0);
			String bedId = result[0].toString();
			if (!BasicUtils.isEmpty(bedId)) {
				dev.setBeddeviceid(bedId);
			}

			String deviceId = result[1].toString();
			if (!BasicUtils.isEmpty(deviceId)) {
				dev.setDevicemoniterid(Long.parseLong(deviceId));
			}

			if (!BasicUtils.isEmpty(meanValMap.get("CO2 RESP RATE"))) {
				dev.setCo2Resprate(String.valueOf(meanValMap.get("CO2 RESP RATE")));
			}

			if (!BasicUtils.isEmpty(meanValMap.get("DIA"))) {
				dev.setDiaBp(String.valueOf(meanValMap.get("DIA")));
			}

			if (!BasicUtils.isEmpty(meanValMap.get("ECG RESP RATE"))) {
				dev.setEcgResprate(String.valueOf(meanValMap.get("ECG RESP RATE")));
			}

			if (!BasicUtils.isEmpty(meanValMap.get("ETCO2"))) {
				dev.setEtco2(String.valueOf(meanValMap.get("ETCO2")));
			}

			if (!BasicUtils.isEmpty(meanValMap.get("HEART RATE"))) {
				dev.setHeartrate(String.valueOf(meanValMap.get("HEART RATE")));
			}

			if (!BasicUtils.isEmpty(meanValMap.get("BP MEAN"))) {
				dev.setMeanBp(String.valueOf("BP MEAN"));
			}

			if (!BasicUtils.isEmpty(meanValMap.get("PULSE"))) {
				dev.setPulserate(String.valueOf("PULSE"));
			}

			if (!BasicUtils.isEmpty(meanValMap.get("SPO2"))) {
				dev.setSpo2(String.valueOf(meanValMap.get("SPO2")));
			}

			if (!BasicUtils.isEmpty(meanValMap.get("SYS"))) {
				dev.setSysBp(String.valueOf(meanValMap.get("SYS")));
			}

			try {
				patientDao.saveObject(dev);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static HashMap<String, Float> calculateMean(String s, HashMap<String, List<Float>> vitalMap) {
		// iterating through the hashmap
		HashMap<String, Float> meanValMap = new HashMap<>();
		for (String id : vitalMap.keySet()) {
			// for each val calculate the mean.
			List<Float> values = vitalMap.get(id);
			if (!BasicUtils.isEmpty(values)) {
				Float sum = Float.parseFloat("0.0");
				for (Float val : values) {
					sum = sum + val;
				}
				Float mean = sum / values.size();
				meanValMap.put(id, mean);
			}
		}
		return meanValMap;
	}

	@Override
	public MonitorDummyDataJson getMonitorData(String uhid, Date currentDate) {
		// reduce the time by one minute.
		Date currentDateMinusTwoMin = new Date(currentDate.getTime() - (2 * 60000));

		// String data = "{\"PLS\":\"123\",\"SpO2\":\"100\",\"HR\":\"119\",\"ART
		// M\":\"87\",\"ART S\":\"125\",\"ART D\":\"56\",\"Ta\":\"22.8\"}";
		MonitorDummyDataJson json = new MonitorDummyDataJson();
		String query = "SELECT * FROM inicu.patient_devicedata_infinity WHERE uhid ='" + uhid.trim() + "' "
				+ "AND device_time>='" + BasicConstants.timeStampFormat.format(currentDateMinusTwoMin) + "' "
				+ "order by device_time limit 1";

		List<DeviceDataInfinity> resultSet = cassandraTemplate.select(query, DeviceDataInfinity.class);
		if (!BasicUtils.isEmpty(resultSet)) {
			DeviceDataInfinity dev = resultSet.get(0);
			if (!BasicUtils.isEmpty(dev)) {
				String data = dev.getValue();
				if (!BasicUtils.isEmpty(data)) {
					try {
						JSONObject jsonObj = new JSONObject(data);
						if (!BasicUtils.isEmpty(jsonObj)) {
							String pulse = jsonObj.get("PLS").toString();
							if (!BasicUtils.isEmpty(pulse)) {
								json.setPulse(pulse);
							}

							String spo2 = jsonObj.get("SpO2").toString();
							if (!BasicUtils.isEmpty(spo2)) {
								json.setSp02(spo2);
							}

							String hr = jsonObj.get("HR").toString();
							if (!BasicUtils.isEmpty(hr)) {
								json.setHeartRate(hr);
							}

							String sys = jsonObj.get("ART S").toString();
							if (!BasicUtils.isEmpty(sys)) {
								json.setSys(sys);
							}

							String dia = jsonObj.get("ART D").toString();
							if (!BasicUtils.isEmpty(dia)) {
								json.setDia(dia);
							}

							String mean = jsonObj.get("ART M").toString();
							if (!BasicUtils.isEmpty(mean)) {
								json.setBpMean(mean);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return json;
	}

	@Override
	public void saveInifinityDataCassandra(Date date, String data, String bedId) {
		if (!BasicUtils.isEmpty(bedId) && !BasicUtils.isEmpty(date) && !BasicUtils.isEmpty(data)) {
			// push the data to cassandra database.
			// here bedId is bedId received from infinity gateway.
			List<String> uhid_ = getUhidFromInfinityBedId(bedId);
			// System.out.println("uhid :"+uhid_);
			// System.out.println("date :"+date);
			if (!BasicUtils.isEmpty(uhid_)) {
				String uhid = uhid_.get(0).toString();
				inifinityPool.execute(new InsertDataThread(date, data, uhid));
			}
		}
	}
	
	@Override
	  public void saveCarescapeACKDataCassandra(String finalPatientID, String finalBedID, String timestamp, Date date,
	      String data) {
	    if (!BasicUtils.isEmpty(data) && !BasicUtils.isEmpty(date.toString())) {
	      carescapeackPool.execute(new InsertDataThread(finalPatientID, finalBedID, timestamp, date, data));
	    }
	  }

	private List<String> getUhidFromInfinityBedId(String bedId) {
		String query = "select uhid from dashboard_finalview where bedid =  "
				+ "(Select inicu_bedid from hl7_device_mapping where hl7_bedid='" + bedId + "' " + "AND isactive=true)";
		List<String> uhid_ = patientDao.getListFromNativeQuery(query);
		return uhid_;
	}

	/**
	 * redirect flow to device integration type.
	 */
	@Override
	public ResponseMessageObject startDeviceMonitoring(AddDeviceTemplateJson deviceData) {
		ResponseMessageObject obj = new ResponseMessageObject();
		obj.setMessage("Device Connected successfully!");
		obj.setType(BasicConstants.MESSAGE_SUCCESS);
		if (!BasicUtils.isEmpty(deviceData)) {
			String brandName = deviceData.getBrandName();
			String deviceType = deviceData.getDeviceType();
			if (!BasicUtils.isEmpty(brandName) && !BasicUtils.isEmpty(deviceType)) {
				obj = connectDevice(deviceData);
			}
		}
		return obj;
	}

	private ResponseMessageObject connectDevice(AddDeviceTemplateJson deviceData) {
		ResponseMessageObject obj = new ResponseMessageObject();
		obj.setMessage("Device connected successfully");
		obj.setType(BasicConstants.MESSAGE_SUCCESS);

		boolean isconnected = true;
		try {
			// connect masimo and get acknowledgment.

			// check if device is already connected.
			boolean isAlreadyConnected = false;
			String fetchDevId = "SELECT deviceid FROM device_detail WHERE available = true " + "AND deviceserialno ='"
					+ deviceData.getPatiendDeviceId() + "'";
			List resultid = patientDao.getListFromNativeQuery(fetchDevId);
			if (!BasicUtils.isEmpty(resultid)) {
				String id = resultid.get(0).toString();
				if (!BasicUtils.isEmpty(id)) {
					// id already exists
					isAlreadyConnected = true;
					isconnected = false;
					obj.setMessage("device is already connected!");
					obj.setType(BasicConstants.MESSAGE_FAILURE);
				}
			}

			if (!isAlreadyConnected) {
				// update table queries.
				String devId = null;
				DeviceDetail devDetail = new DeviceDetail();
				devDetail.setAvailable(true);
				String[] deviceDetail = deviceData.getBrandName().split("\\|");
				if (deviceDetail.length > 1) {
					devDetail.setDevicename(deviceDetail[0].toString().trim());
					devDetail.setDevicebrand(deviceDetail[1].toString().trim());
					devDetail.setDevicetype(deviceData.getDeviceType());
				}
				// devDetail.setDomainid(deviceData.getDomainId());
				// devDetail.setDeviceid(Long.parseLong(deviceData.getPatiendDeviceId()));
				devDetail.setDeviceserialno(deviceData.getPatiendDeviceId());
				try {
					devDetail = (DeviceDetail) patientDao.saveObject(devDetail);
					if (!BasicUtils.isEmpty(devDetail.getDeviceid())) {
						devId = String.valueOf(devDetail.getDeviceid());
					}
					// String query = "SELECT deviceid FROM device_detail WHERE
					// deviceserialno='"
					// + deviceData.getPatiendDeviceId() + "'";
					// List<String> result =
					// patientDao.getListFromNativeQuery(query);
					// if (!BasicUtils.isEmpty(result)) {
					// devId = String.valueOf(result.get(0));
					// }
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (!BasicUtils.isEmpty(devId)) {
					BedDeviceDetail bedDevDetail = new BedDeviceDetail();
					bedDevDetail.setBedid(deviceData.getBedId());
					bedDevDetail.setDeviceid(devId);
					try {
						patientDao.saveObject(bedDevDetail);
					} catch (Exception e) {
						// roll back device entry.
						e.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			isconnected = false;
			obj.setMessage(e.getMessage());
			obj.setType(BasicConstants.MESSAGE_FAILURE);
		}

		return obj;
	}

	@Override
	public MonitorWaveformData getDeviceWaveFormData(String uhid, Date currentTime) {
		String givenTime = CalendarUtility.getDateformatampm(CalendarUtility.SERVER_CRUD_OPERATION).format(currentTime);
		String query = "SELECT obj FROM DeviceMonitorDetail as obj WHERE uhid='" + uhid + "' AND " + "starttime >= '"
				+ givenTime + "'";
		List<DeviceMonitorDetail> resultSet = patientDao.getListFromMappedObjNativeQuery(query);
		if (!BasicUtils.isEmpty(resultSet)) {

		}

		return null;
	}

	@Override
	public String getUhidFromDeviceId(String devId) {
		String uhid = null;
		String query = "select uhid from bed_device_history " + "where deviceserialno = '" + devId.trim() + "'"
				+ "AND available = true";

		List<String> resultSet = patientDao.getListFromNativeQuery(query);
		if (!BasicUtils.isEmpty(resultSet)) {
			uhid = resultSet.get(0).toString();
		}
		return uhid;
	}

	@Override
	public ResponseMessageObject disconnectDevice(String uhid, String deviceId) {
		ResponseMessageObject rObj = new ResponseMessageObject();
		rObj.setType(BasicConstants.MESSAGE_SUCCESS);
		rObj.setMessage("Device disconnected successfully.");
		try {

			String query = "Select obj FROM DeviceDetail as obj WHERE deviceid='" + deviceId + "' ";
			List<DeviceDetail> resultSet = patientDao.getListFromMappedObjNativeQuery(query);
			if (!BasicUtils.isEmpty(resultSet)) {
				DeviceDetail dev = resultSet.get(0);
				if (!BasicUtils.isEmpty(dev)) {
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					dev.setEndtime(timestamp);
					dev.setAvailable(false);

					patientDao.saveObject(dev);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			rObj.setType(BasicConstants.MESSAGE_FAILURE);
			rObj.setMessage(e.getMessage());
		}

		return rObj;
	}

	@SuppressWarnings({"unchecked", "deprecation"})
	@Override
	public List<VitalCerebralExportPOJO> getExportVitalData(String uhid, String entryDate, String endDate) {
		List<VwVitalCerebralExport> obj = new ArrayList<VwVitalCerebralExport>();
		List<VitalCerebralExportPOJO> returnObj = new ArrayList<VitalCerebralExportPOJO>();

		try {
			Date entryTimeStamp = CalendarUtility.getDateFormatGMT(CalendarUtility.CLIENT_CRUD_OPERATION).parse(entryDate);

			Date entryEndTimeStamp = CalendarUtility.getDateFormatGMT(CalendarUtility.CLIENT_CRUD_OPERATION).parse(endDate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");

			String entryTimeServerFormat = formatter.format(entryTimeStamp);
			String entryEndTimeServerFormat = formatter.format(entryEndTimeStamp);

			String query = "SELECT obj FROM VwVitalCerebralExport as obj WHERE " + "uhid ='"
					+ uhid.trim() + "' " + "AND devicetime >= '" + entryTimeServerFormat
					+ "' AND devicetime <= '" + entryEndTimeServerFormat + "' "
					+ " order by devicetime";

			obj = patientDao.getListFromMappedObjNativeQuery(query);
			for (VwVitalCerebralExport result : obj) {
				VitalCerebralExportPOJO newObj = new VitalCerebralExportPOJO();

				if (!BasicUtils.isEmpty(result.getUhid()))
					newObj.setUhid(result.getUhid());

				if (!BasicUtils.isEmpty(result.getDeviceTime()))
					newObj.setDeviceTime(result.getDeviceTime().toString());

				if (!BasicUtils.isEmpty(result.getHeartRate()))
					newObj.setHeartRate(result.getHeartRate());

				if (!BasicUtils.isEmpty(result.getPulseRate()))
					newObj.setPulseRate(result.getPulseRate());

				if (!BasicUtils.isEmpty(result.getSpo2()))
					newObj.setSpo2(result.getSpo2());

				if (!BasicUtils.isEmpty(result.getRespiratoryRate()))
					newObj.setRespiratoryRate(result.getRespiratoryRate());

				if (!BasicUtils.isEmpty(result.getSystolicBp()))
					newObj.setSystolicBp(result.getSystolicBp());

				if (!BasicUtils.isEmpty(result.getDiastolicBp()))
					newObj.setDiastolicBp(result.getDiastolicBp());

				if (!BasicUtils.isEmpty(result.getMeanBp()))
					newObj.setMeanBp(result.getMeanBp());

				if (!BasicUtils.isEmpty(result.getSystolicNbp()))
					newObj.setSystolicNbp(result.getSystolicNbp());

				if (!BasicUtils.isEmpty(result.getDiastolicNbp()))
					newObj.setDiastolicNbp(result.getDiastolicNbp());

				if (!BasicUtils.isEmpty(result.getMeanNbp()))
					newObj.setMeanNbp(result.getMeanNbp());

				if (!BasicUtils.isEmpty(result.getSystolicIbp()))
					newObj.setSystolicIbp(result.getSystolicIbp());

				if (!BasicUtils.isEmpty(result.getDiastolicIbp()))
					newObj.setDiastolicIbp(result.getDiastolicIbp());

				if (!BasicUtils.isEmpty(result.getMeanIbp()))
					newObj.setMeanIbp(result.getMeanIbp());

				if (!BasicUtils.isEmpty(result.getPi()))
					newObj.setPi(result.getPi());

				if (!BasicUtils.isEmpty(result.getFio2()))
					newObj.setFio2(result.getFio2());

				if (!BasicUtils.isEmpty(result.getPip()))
					newObj.setPip(result.getPip());

				if (!BasicUtils.isEmpty(result.getPeep()))
					newObj.setPeep(result.getPeep());

				returnObj.add(newObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnObj;
	}

	public VitalRangeJSON getMinMaxRange() {
		VitalRangeJSON range = new VitalRangeJSON();
		MinMaxRange hrRange = new MinMaxRange();
		hrRange.setMax(400);
		hrRange.setMin(0);
		MinMaxRange rrRange = new MinMaxRange();
		rrRange.setMax(100);
		rrRange.setMin(0);
		MinMaxRange spo2Range = new MinMaxRange();
		spo2Range.setMax(100);
		spo2Range.setMin(0);
		MinMaxRange pipRange = new MinMaxRange();
		pipRange.setMax(100);
		pipRange.setMin(0);
		MinMaxRange peepRange = new MinMaxRange();
		peepRange.setMax(100);
		peepRange.setMin(0);
		MinMaxRange fio2Range = new MinMaxRange();
		fio2Range.setMax(100);
		fio2Range.setMin(20);
		MinMaxRange rateRange = new MinMaxRange();
		rateRange.setMax(100);
		rateRange.setMin(10);
		MinMaxRange mapRange = new MinMaxRange();
		mapRange.setMax(50);
		mapRange.setMin(0);

		// NIBP
		MinMaxRange nbp_sRange = new MinMaxRange();
		nbp_sRange.setMin(0);
		nbp_sRange.setMax(240);

		MinMaxRange nbp_dRange = new MinMaxRange();
		nbp_dRange.setMin(0);
		nbp_dRange.setMax(240);

		MinMaxRange nbp_mRange = new MinMaxRange();
		nbp_mRange.setMin(0);
		nbp_mRange.setMax(240);

		// IBP
		MinMaxRange ibp_sRange = new MinMaxRange();
		ibp_sRange.setMin(0);
		ibp_sRange.setMax(240);

		MinMaxRange ibp_dRange = new MinMaxRange();
		ibp_dRange.setMin(0);
		ibp_dRange.setMax(240);

		MinMaxRange ibp_mRange = new MinMaxRange();
		ibp_mRange.setMin(0);
		ibp_mRange.setMax(240);

		// Central Temperature
		MinMaxRange central_temp = new MinMaxRange();
		central_temp.setMin(25);
		central_temp.setMax(44);

		// Peripheral Temperature
		MinMaxRange periheral_temp = new MinMaxRange();
		periheral_temp.setMin(25);
		periheral_temp.setMax(44);

		// Humidity
		MinMaxRange humidity = new MinMaxRange();
		humidity.setMin(25);
		humidity.setMax(44);

		range.setHr(hrRange);
		range.setRr(rrRange);
		range.setSpo2(spo2Range);
		range.setPip(pipRange);
		range.setPeep(peepRange);
		range.setFio2(fio2Range);
		range.setRate(rateRange);
		range.setMap(mapRange);

		range.setNbp_s(nbp_sRange);
		range.setNbp_d(nbp_dRange);
		range.setNbp_m(nbp_mRange);

		range.setIbp_s(ibp_sRange);
		range.setIbp_d(ibp_dRange);
		range.setIbp_m(ibp_mRange);

		range.setCentral_temperature(central_temp);
		range.setPeripheral_temperature(periheral_temp);
		range.setHumidity(humidity);

		return range;
	}

	public DeviceGraphDataJson getDeviceMonitorData(String uhid, String entryTimeServerFormat, String entryEndTimeServerFormat, int overDelay, DeviceGraphDataJson obj, String isHourly) {

		List<List<Object>> hrFinalData = new ArrayList<>();
		List<String> hrxAxisData = new ArrayList<String>();
		List<Float> hryAxisData = new ArrayList<Float>();
		
		List<List<Object>> eventFinalData = new ArrayList<>();
		List<String> eventxAxisData = new ArrayList<String>();
		List<Float> eventyAxisData = new ArrayList<Float>();
		
		List<List<Object>> hdpFinalData = new ArrayList<>();
		List<String> hdpxAxisData = new ArrayList<String>();
		List<Float> hdpyAxisData = new ArrayList<Float>();

		List<List<Object>> rrFinalData = new ArrayList<>();
		List<String> rRxAxisData = new ArrayList<String>();
		List<Float> rRyAxisData = new ArrayList<Float>();

		List<List<Object>> spo2FinalData = new ArrayList<>();
		List<String> sPO2xAxisData = new ArrayList<String>();
		List<Float> sPO2yAxisData = new ArrayList<Float>();

		List<List<Object>> pulseFinalData = new ArrayList<>();
		List<String> pulsexAxisData = new ArrayList<String>();
		List<Float> pulseyAxisData = new ArrayList<Float>();

		List<List<Object>> piFinalData = new ArrayList<>();
		List<List<Object>> o3rSO2_1FinalData = new ArrayList<>();
		List<List<Object>> o3rSO2_2FinalData = new ArrayList<>();

		List<List<Object>> nbpSFinalData = new ArrayList<>();
		List<String> nbpSxAxisData = new ArrayList<String>();
		List<Float> nbpSyAxisData = new ArrayList<Float>();

		List<List<Object>> nbpDFinalData = new ArrayList<>();
		List<String> nbpDxAxisData = new ArrayList<String>();
		List<Float> nbpDyAxisData = new ArrayList<Float>();

		List<List<Object>> nbpMFinalData = new ArrayList<>();
		List<String> nbpMxAxisData = new ArrayList<String>();
		List<Float> nbpMyAxisData = new ArrayList<Float>();

		List<List<Object>> ibpSFinalData = new ArrayList<>();
		List<String> ibpSxAxisData = new ArrayList<String>();
		List<Float> ibpSyAxisData = new ArrayList<Float>();

		List<List<Object>> ibpDFinalData = new ArrayList<>();
		List<String> ibpDxAxisData = new ArrayList<String>();
		List<Float> ibpDyAxisData = new ArrayList<Float>();

		List<List<Object>> ibpMFinalData = new ArrayList<>();
		List<String> ibpMxAxisData = new ArrayList<String>();
		List<Float> ibpMyAxisData = new ArrayList<Float>();
		
		List<List<Object>> nSofaFinalData = new ArrayList<>();
		List<String> nSofaxAxisData = new ArrayList<String>();
		List<Float> nSofayAxisData = new ArrayList<Float>();

		List<List<Object>> inotropeFinalData = new ArrayList<>();
		List<List<Object>> spo2Fio2RatioFinalData = new ArrayList<>();

		// yyyy-MM-dd hh:mm
		String currentTime = "";
		String previousTime = "";
		Timestamp currentHourTime = null;
		Timestamp previousHourTime = null;
		boolean statusFlag = false;
		ArrayList<String> allxAxisData = new ArrayList<>();
		
		String babyDetailListQuery = "select obj from BabyDetail as obj where uhid='" + uhid + "'";
		List<BabyDetail> babyDetailList = patientDao.getListFromMappedObjNativeQuery(babyDetailListQuery);
		if(babyDetailList != null && babyDetailList.size() > 0) {
			String branchName = babyDetailList.get(0).getBranchname();
			if(branchName != null && branchName != "") {
				String refHospitalbranchnameQuery = "select obj from RefHospitalbranchname as obj where branchname='" + branchName + "'";
				List<RefHospitalbranchname> refHospitalbranchnameList = inicuDao.getListFromMappedObjQuery(refHospitalbranchnameQuery);
				if(refHospitalbranchnameList != null && refHospitalbranchnameList.size() > 0 && !BasicUtils.isEmpty(refHospitalbranchnameList.get(0).getPredictions())) {
					String prediction = refHospitalbranchnameList.get(0).getPredictions();
					if(prediction.equalsIgnoreCase("Yes")) {
			
						String query = "SELECT obj FROM PredictHDP as obj WHERE " + "uhid ='"
								+ uhid.trim() + "' " + "AND hdptime >= '" + entryTimeServerFormat
								+ "' AND hdptime <= '" + entryEndTimeServerFormat + "' "
								+ " order by hdptime";
						
						List<PredictHDP> resultSet = patientDao.getListFromMappedObjNativeQuery(query);

						// HDP Data
						if (!BasicUtils.isEmpty(resultSet)) {
							for (PredictHDP dev : resultSet) {
								List<Object> hdpData = new ArrayList<>();
								
								
								hdpData.add(dev.getHdptime());
								hdpData.add(dev.getHdpvalue());
								hdpFinalData.add(hdpData);
								//	                            hryAxisData.add(xdata);
								Date dateTime = dev.getHdptime();
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								//	                            hrxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}
							obj.setHDPObject(hdpFinalData);
							DeviceGraphAxisJson hdpAxis = new DeviceGraphAxisJson();
							hdpAxis.setxAxis(hdpxAxisData);
							hdpAxis.setyAxis(hdpyAxisData);
						}
					}
				}
			}
		}

		String query = "SELECT obj FROM DeviceMonitorDetail as obj WHERE " + "uhid ='"
				+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
				+ "' AND starttime <= '" + entryEndTimeServerFormat + "' "
				+ " order by starttime";

		List<DeviceMonitorDetail> resultSet = patientDao.getListFromMappedObjNativeQuery(query);
		
		query = "SELECT obj FROM TestItemResult as obj WHERE prn ='"
				+ uhid.trim() + "' and (itemname = 'PLATELET COUNT') order by lab_report_date asc";

		List<TestItemResult> resultSetHema = patientDao.getListFromMappedObjNativeQuery(query);
		List<ArrayList<Object>> plateletCountMapFinal = new ArrayList<ArrayList<Object>>();

		for(TestItemResult objTest : resultSetHema) {
			try {
				ArrayList<Object> plateletCountMapObject = new ArrayList<Object>();

				if(Integer.parseInt(objTest.getItemvalue()) > 2000) {
					if(Integer.parseInt(objTest.getItemvalue()) >= 150000) {
						plateletCountMapObject.add(objTest.getLabReportDate());
						plateletCountMapObject.add(0);

					}else if(Integer.parseInt(objTest.getItemvalue()) < 150000 && 
							Integer.parseInt(objTest.getItemvalue()) >= 100000) {
						plateletCountMapObject.add(objTest.getLabReportDate());
						plateletCountMapObject.add(1);
					}else if(Integer.parseInt(objTest.getItemvalue()) < 100000 && 
							Integer.parseInt(objTest.getItemvalue()) >= 50000) {
						plateletCountMapObject.add(objTest.getLabReportDate());
						plateletCountMapObject.add(2);
					}else if(Integer.parseInt(objTest.getItemvalue()) < 50000) {
						plateletCountMapObject.add(objTest.getLabReportDate());
						plateletCountMapObject.add(3);
					}
					
				}else {
					if(Integer.parseInt(objTest.getItemvalue()) >= 150) {
						plateletCountMapObject.add(objTest.getLabReportDate());
						plateletCountMapObject.add(0);
					}else if(Integer.parseInt(objTest.getItemvalue()) < 150 && 
							Integer.parseInt(objTest.getItemvalue()) >= 100) {
						plateletCountMapObject.add(objTest.getLabReportDate());
						plateletCountMapObject.add(1);
					}else if(Integer.parseInt(objTest.getItemvalue()) < 100 && 
							Integer.parseInt(objTest.getItemvalue()) >= 500) {
						plateletCountMapObject.add(objTest.getLabReportDate());
						plateletCountMapObject.add(2);
					}else if(Integer.parseInt(objTest.getItemvalue()) < 500) {
						plateletCountMapObject.add(objTest.getLabReportDate());
						plateletCountMapObject.add(3);
					}
				}
				plateletCountMapFinal.add(plateletCountMapObject);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		query = "SELECT obj FROM BabyPrescription as obj WHERE " + "uhid ='"
				+ uhid.trim() + "' AND medicationtype = 'TYPE0004' order by startdate asc";

		List<BabyPrescription> resultSetcardio = patientDao.getListFromMappedObjNativeQuery(query);
		List<ArrayList<Object>> prescriptionListObjectFinal = new ArrayList<ArrayList<Object>>();
		try {
			for(BabyPrescription prescriptionObj : resultSetcardio) {
				ArrayList<Object> prescriptionListObject = new ArrayList<Object>();
				prescriptionListObject.add(prescriptionObj.getStartdate());
				
				if(!BasicUtils.isEmpty(prescriptionObj.getEnddate()))
					prescriptionListObject.add(prescriptionObj.getEnddate());
				else {
					prescriptionListObject.add(new Timestamp(new Date().getTime()));
				}
				prescriptionListObject.add(prescriptionObj.getMedicinename());

				prescriptionListObjectFinal.add(prescriptionListObject);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();
		HashMap<Timestamp,Integer> ventilationCountMap = new HashMap<Timestamp,Integer>();

		try {
			query = "SELECT obj FROM NursingVentilaor as obj WHERE uhid ='"
					+ uhid.trim() + "' AND (ventmode = 'VM0004' OR ventmode = 'VM0005' OR ventmode = 'VM0006' OR ventmode = 'VM0016' OR ventmode = 'VM0019') AND fio2 is not null order by entryDate desc";
			List<NursingVentilaor> resultSetresp = patientDao.getListFromMappedObjNativeQuery(query);
			for(NursingVentilaor objTest : resultSetresp) {
				ventilationCountMap.put(new Timestamp(objTest.getEntryDate().getTime() - offset),Integer.parseInt(objTest.getFio2()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		// Device Monitor Detail Data
		if (!BasicUtils.isEmpty(resultSet)) {

			if (isHourly.equalsIgnoreCase("daily")) {
				// Hourly
				for (DeviceMonitorDetail dev : resultSet) {
					if (dev != null) {
						if (!statusFlag) {
							long oneHour = 5 * 60 * 60 * 1000;
							previousHourTime = new Timestamp(dev.getStarttime().getTime() - oneHour);
							statusFlag = true;
						}

						// iterate over vent.
						Timestamp time = dev.getStarttime();
						if (overDelay != 0) {
							Long newTime = time.getTime() + overDelay;
							time = new Timestamp(newTime);
						} else {
							new Timestamp(time.getTime());
						}

						currentHourTime = dev.getStarttime();
						currentTime = dev.getStarttime().toString().substring(0, 16);

						if (!currentTime.equalsIgnoreCase(previousTime)
								&& ((currentHourTime.getTime() - previousHourTime.getTime()) > (60 * 60 * 1000))) {
							
							Integer respiratoryScore = 0;
							Integer cardiovascularScore = 0;
							Integer hematologicScore = 0;
							
							for(List<Object> plateletCountMapObject : plateletCountMapFinal) {
								Timestamp labReportDate = (Timestamp) plateletCountMapObject.get(0);
								Integer evaluation = (Integer) plateletCountMapObject.get(1);
								
								if(currentHourTime.getTime() >= labReportDate.getTime()) {
									hematologicScore = evaluation;
								}			
							}
							
							List<String> prescriptionList = new ArrayList<String>();
							
							Integer isInotrope = 0;

							for(List<Object> prescriptionListObject : prescriptionListObjectFinal) {
								Timestamp startDateCardio = (Timestamp) prescriptionListObject.get(0);
								Timestamp endDateCardio = (Timestamp) prescriptionListObject.get(1);
								String medicineNameCardio = (String) prescriptionListObject.get(2);
								
								if(currentHourTime.getTime() >= startDateCardio.getTime() && currentHourTime.getTime() <= endDateCardio.getTime() && !prescriptionList.contains(medicineNameCardio)) {
									prescriptionList.add(medicineNameCardio);
									isInotrope = 1;
								}
								if(prescriptionList.size() == 1) {
									cardiovascularScore = 2;
								}else if(prescriptionList.size() == 2) {
									cardiovascularScore = 4;
								}
										
							}
							
							Integer fio2 = -1;

							for(Timestamp entryDate : ventilationCountMap.keySet()) {								
								if(currentHourTime.getTime() >= entryDate.getTime() && ((currentHourTime.getTime() - entryDate.getTime()) <= (2*60*60*1000))) {
									fio2 = ventilationCountMap.get(entryDate);
								}
							}
							
							float respFactor = 4;
							String spo2 = dev.getSpo2();
							if (!BasicUtils.isEmpty(spo2)) {
								List<Object> spo2Data = new ArrayList<>();
								float xdata = Float.parseFloat(spo2);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								spo2Data.add(time.getTime());
								spo2Data.add(xdata);
								spo2FinalData.add(spo2Data);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								//	                            sPO2xAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
								
								respFactor = xdata/fio2;
							}
							
							if(fio2 != -1) {
							
								if(respFactor >= 3) {
									respiratoryScore = 0;
								}else if(respFactor < 3 && respFactor >= 2) {
									respiratoryScore = 2;
								}else if(respFactor < 2 && respFactor >= 1.5) {
									respiratoryScore = 4;
								}else if(respFactor < 1.5 && respFactor >= 1) {
									respiratoryScore = 6;
								}else {
									respiratoryScore = 8;
								}
							}else {
								respFactor = 4;
							}
							
							Integer totalnSofaScore = respiratoryScore + cardiovascularScore + hematologicScore;
							List<Object> nSofaData = new ArrayList<>();
							
							nSofaData.add(time.getTime());
							nSofaData.add(totalnSofaScore);
							nSofaFinalData.add(nSofaData);
							
							List<Object> inotropeData = new ArrayList<>();
							inotropeData.add(time.getTime());
							inotropeData.add(isInotrope);
							inotropeFinalData.add(inotropeData);
							
							List<Object> spo2Fio2RatioData = new ArrayList<>();
							spo2Fio2RatioData.add(time.getTime());
							spo2Fio2RatioData.add(respFactor);
							spo2Fio2RatioFinalData.add(spo2Fio2RatioData);							

							// nbp_s
							String nbp_s = dev.getNbp_s();
							if (!BasicUtils.isEmpty(nbp_s)) {
								List<Object> nbp_sData = new ArrayList<>();
								float xdata = Float.parseFloat(nbp_s);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0) / 10.0);
								nbp_sData.add(time.getTime());
								nbp_sData.add(xdata);
								nbpSFinalData.add(nbp_sData);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							// nbp_d
							String nbp_d = dev.getNbp_d();
							if (!BasicUtils.isEmpty(nbp_d)) {
								List<Object> nbp_dData = new ArrayList<>();
								float xdata = Float.parseFloat(nbp_d);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0) / 10.0);
								nbp_dData.add(time.getTime());
								nbp_dData.add(xdata);
								nbpDFinalData.add(nbp_dData);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							// nbp_m
							String nbp_m = dev.getNbp_m();
							if (!BasicUtils.isEmpty(nbp_m)) {
								List<Object> nbp_mData = new ArrayList<>();
								float xdata = Float.parseFloat(nbp_m);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								nbp_mData.add(time.getTime());
								nbp_mData.add(xdata);
								nbpMFinalData.add(nbp_mData);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							// ibp_s
							String ibp_s = dev.getIbp_s();
							if (!BasicUtils.isEmpty(ibp_s)) {
								List<Object> ibp_sData = new ArrayList<>();
								float xdata = Float.parseFloat(ibp_s);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								ibp_sData.add(time.getTime());
								ibp_sData.add(xdata);
								ibpSFinalData.add(ibp_sData);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							// ibp_d
							String ibp_d = dev.getIbp_d();
							if (!BasicUtils.isEmpty(ibp_d)) {
								List<Object> ibp_dData = new ArrayList<>();
								float xdata = Float.parseFloat(ibp_d);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								ibp_dData.add(time.getTime());
								ibp_dData.add(xdata);
								ibpDFinalData.add(ibp_dData);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							// ibp_m
							String ibp_m = dev.getIbp_m();
							if (!BasicUtils.isEmpty(ibp_m)) {
								List<Object> ibp_mData = new ArrayList<>();
								float xdata = Float.parseFloat(ibp_m);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								ibp_mData.add(time.getTime());
								ibp_mData.add(xdata);
								ibpMFinalData.add(ibp_mData);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							String hr = dev.getHeartrate();
							if (!BasicUtils.isEmpty(hr)) {
								List<Object> hrData = new ArrayList<>();
								float xdata = Float.parseFloat(hr);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								hrData.add(time.getTime());
								hrData.add(xdata);
								hrFinalData.add(hrData);
								//	                            hryAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								//	                            hrxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							String rr = dev.getEcgResprate();
							if (!BasicUtils.isEmpty(rr)) {
								List<Object> rrData = new ArrayList<>();
								float xdata = Float.parseFloat(rr);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								rrData.add(time.getTime());
								rrData.add(xdata);
								rrFinalData.add(rrData);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								//	                            rRxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							String pulse = dev.getPulserate();
							if (!BasicUtils.isEmpty(pulse)) {
								List<Object> pulseData = new ArrayList<>();
								float xdata = Float.parseFloat(pulse);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								pulseData.add(time.getTime());
								pulseData.add(xdata);
								pulseFinalData.add(pulseData);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								//	                            pulsexAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							

							String pi = dev.getPi();
							if (!BasicUtils.isEmpty(pi)) {
								List<Object> piData = new ArrayList<>();
								float xdata = Float.parseFloat(pi);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								piData.add(time.getTime());
								piData.add(xdata);
								piFinalData.add(piData);
							}

							String O3rSO2_1 = dev.getO3rSO2_1();
							if (!BasicUtils.isEmpty(O3rSO2_1)) {
								List<Object> O3rSO2_1Data = new ArrayList<>();
								float xdata = Float.parseFloat(O3rSO2_1);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								O3rSO2_1Data.add(time.getTime());
								O3rSO2_1Data.add(xdata);
								o3rSO2_1FinalData.add(O3rSO2_1Data);
							}

							String O3rSO2_2 = dev.getO3rSO2_2();
							if (!BasicUtils.isEmpty(O3rSO2_2)) {
								List<Object> O3rSO2_2Data = new ArrayList<>();
								float xdata = Float.parseFloat(O3rSO2_2);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								O3rSO2_2Data.add(time.getTime());
								O3rSO2_2Data.add(xdata);
								o3rSO2_2FinalData.add(O3rSO2_2Data);
							}

							previousTime = currentTime;
							previousHourTime = currentHourTime;
						}
					}
				}
			} else if(isHourly.equalsIgnoreCase("hourly")){
				// Minute Wise
				for (DeviceMonitorDetail dev : resultSet) {
					if (dev != null) {
						// iterate over vent.
						Timestamp time = dev.getStarttime();
						if (overDelay != 0) {
							Long newTime = time.getTime() + overDelay;
							time = new Timestamp(newTime);
						}

						currentTime = dev.getStarttime().toString().substring(0, 16);

						if (!currentTime.equalsIgnoreCase(previousTime)) {
							
							Integer respiratoryScore = 0;
							Integer cardiovascularScore = 0;
							Integer hematologicScore = 0;
							
							for(List<Object> plateletCountMapObject : plateletCountMapFinal) {
								Timestamp labReportDate = (Timestamp) plateletCountMapObject.get(0);
								Integer evaluation = (Integer) plateletCountMapObject.get(1);
								
								if(dev.getStarttime().getTime() >= labReportDate.getTime()) {
									hematologicScore = evaluation;
								}			
							}
							
							List<String> prescriptionList = new ArrayList<String>();
							Integer isInotrope = 0;

							for(List<Object> prescriptionListObject : prescriptionListObjectFinal) {
								Timestamp startDateCardio = (Timestamp) prescriptionListObject.get(0);
								Timestamp endDateCardio = (Timestamp) prescriptionListObject.get(1);
								String medicineNameCardio = (String) prescriptionListObject.get(2);
								
								if(dev.getStarttime().getTime() >= startDateCardio.getTime() && dev.getStarttime().getTime() <= endDateCardio.getTime() && !prescriptionList.contains(medicineNameCardio)) {
									isInotrope = 1;
									prescriptionList.add(medicineNameCardio);
								}
								if(prescriptionList.size() == 1) {
									cardiovascularScore = 2;
								}else if(prescriptionList.size() == 2) {
									cardiovascularScore = 4;
								}
										
							}
							
							
							Integer fio2 = -1;

							for(Timestamp entryDate : ventilationCountMap.keySet()) {								
								if(dev.getStarttime().getTime() >= entryDate.getTime() && ((dev.getStarttime().getTime() - entryDate.getTime()) <= (2*60*60*1000))) {
									fio2 = ventilationCountMap.get(entryDate);
								}
							}
							
							float respFactor = 4;
							String spo2 = dev.getSpo2();
							if (!BasicUtils.isEmpty(spo2)) {
								List<Object> spo2Data = new ArrayList<>();
								float xdata = Float.parseFloat(spo2);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								sPO2yAxisData.add(xdata);
								//							          Timestamp time = dev.getStarttime();
								//							          Timestamp offsetTime = new Timestamp(time.getTime());
								spo2Data.add(time.getTime());
								spo2Data.add(xdata);
								spo2FinalData.add(spo2Data);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								sPO2xAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
								respFactor = xdata/fio2;
							}
							if(fio2 != -1) {
							
								if(respFactor >= 3) {
									respiratoryScore = 0;
								}else if(respFactor < 3 && respFactor >= 2) {
									respiratoryScore = 2;
								}else if(respFactor < 2 && respFactor >= 1.5) {
									respiratoryScore = 4;
								}else if(respFactor < 1.5 && respFactor >= 1) {
									respiratoryScore = 6;
								}else {
									respiratoryScore = 8;
								}
							}else {
								respFactor = 4;
							}
							
							
							Integer totalnSofaScore = respiratoryScore + cardiovascularScore + hematologicScore;
							List<Object> nSofaData = new ArrayList<>();
							
							nSofaData.add(time.getTime());
							nSofaData.add(totalnSofaScore);
							nSofaFinalData.add(nSofaData);
							
							List<Object> inotropeData = new ArrayList<>();
							inotropeData.add(time.getTime());
							inotropeData.add(isInotrope);
							inotropeFinalData.add(inotropeData);
							
							List<Object> spo2Fio2RatioData = new ArrayList<>();
							spo2Fio2RatioData.add(time.getTime());
							spo2Fio2RatioData.add(respFactor);
							spo2Fio2RatioFinalData.add(spo2Fio2RatioData);		

							// nbp_s
							String nbp_s = dev.getNbp_s();
							if (!BasicUtils.isEmpty(nbp_s)) {
								List<Object> nbp_sData = new ArrayList<>();
								float xdata = Float.parseFloat(nbp_s);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								nbp_sData.add(time.getTime());
								nbp_sData.add(xdata);
								nbpSFinalData.add(nbp_sData);
								nbpSyAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								nbpSxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							// nbp_d
							String nbp_d = dev.getNbp_d();
							if (!BasicUtils.isEmpty(nbp_d)) {
								List<Object> nbp_dData = new ArrayList<>();
								float xdata = Float.parseFloat(nbp_d);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//										  Timestamp time = dev.getStarttime();
								//										  Timestamp offsetTime = new Timestamp(time.getTime());
								nbp_dData.add(time.getTime());
								nbp_dData.add(xdata);
								nbpDFinalData.add(nbp_dData);
								nbpDyAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								nbpDxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							// nbp_m
							String nbp_m = dev.getNbp_m();
							if (!BasicUtils.isEmpty(nbp_m)) {
								List<Object> nbp_mData = new ArrayList<>();
								float xdata = Float.parseFloat(nbp_m);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//										  Timestamp time = dev.getStarttime();
								//										  Timestamp offsetTime = new Timestamp(time.getTime() );
								nbp_mData.add(time.getTime());
								nbp_mData.add(xdata);
								nbpMFinalData.add(nbp_mData);
								nbpMyAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								nbpMxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							// ibp_s
							String ibp_s = dev.getIbp_s();
							if (!BasicUtils.isEmpty(ibp_s)) {
								List<Object> ibp_sData = new ArrayList<>();
								float xdata = Float.parseFloat(ibp_s);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//										  Timestamp time = dev.getStarttime();
								//										  Timestamp offsetTime = new Timestamp(time.getTime() );
								ibp_sData.add(time.getTime());
								ibp_sData.add(xdata);
								ibpSFinalData.add(ibp_sData);
								ibpSyAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								ibpSxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							// ibp_d
							String ibp_d = dev.getIbp_d();
							if (!BasicUtils.isEmpty(ibp_d)) {
								List<Object> ibp_dData = new ArrayList<>();
								float xdata = Float.parseFloat(ibp_d);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//										  Timestamp time = dev.getStarttime();
								//										  Timestamp offsetTime = new Timestamp(time.getTime());
								ibp_dData.add(time.getTime());
								ibp_dData.add(xdata);
								ibpDFinalData.add(ibp_dData);
								ibpDyAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								ibpDxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							// ibp_m
							String ibp_m = dev.getIbp_m();
							if (!BasicUtils.isEmpty(ibp_m)) {
								List<Object> ibp_mData = new ArrayList<>();
								float xdata = Float.parseFloat(ibp_m);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//										  Timestamp time = dev.getStarttime();
								//										  Timestamp offsetTime = new Timestamp(time.getTime());
								ibp_mData.add(time.getTime());
								ibp_mData.add(xdata);
								ibpMFinalData.add(ibp_mData);
								ibpMyAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								ibpMxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							String hr = dev.getHeartrate();
							if (!BasicUtils.isEmpty(hr)) {
								List<Object> hrData = new ArrayList<>();
								float xdata = Float.parseFloat(hr);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//							          Timestamp time = dev.getStarttime();
								//							          Timestamp offsetTime = new Timestamp(time.getTime());
								hrData.add(time.getTime());
								hrData.add(xdata);
								hrFinalData.add(hrData);
								hryAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								hrxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							String rr = dev.getEcgResprate();
							if (!BasicUtils.isEmpty(rr)) {
								List<Object> rrData = new ArrayList<>();
								float xdata = Float.parseFloat(rr);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								rRyAxisData.add(xdata);
								//							          Timestamp time = dev.getStarttime();
								//							          Timestamp offsetTime = new Timestamp(time.getTime() );
								rrData.add(time.getTime());
								rrData.add(xdata);
								rrFinalData.add(rrData);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								rRxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							String pulse = dev.getPulserate();
							if (!BasicUtils.isEmpty(pulse)) {
								List<Object> pulseData = new ArrayList<>();
								float xdata = Float.parseFloat(pulse);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								pulseyAxisData.add(xdata);
								//							          Timestamp time = dev.getStarttime();
								//							          Timestamp offsetTime = new Timestamp(time.getTime() );
								pulseData.add(time.getTime());
								pulseData.add(xdata);
								pulseFinalData.add(pulseData);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								pulsexAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							String pi = dev.getPi();
							if (!BasicUtils.isEmpty(pi)) {
								List<Object> piData = new ArrayList<>();
								float xdata = Float.parseFloat(pi);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//							          Timestamp time = dev.getStarttime();
								//							          Timestamp offsetTime = new Timestamp(time.getTime());
								piData.add(time.getTime());
								piData.add(xdata);
								piFinalData.add(piData);
							}

							String O3rSO2_1 = dev.getO3rSO2_1();
							if (!BasicUtils.isEmpty(O3rSO2_1)) {
								List<Object> O3rSO2_1Data = new ArrayList<>();
								float xdata = Float.parseFloat(O3rSO2_1);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//							          Timestamp time = dev.getStarttime();
								//							          Timestamp offsetTime = new Timestamp(time.getTime());
								O3rSO2_1Data.add(time.getTime());
								O3rSO2_1Data.add(xdata);
								o3rSO2_1FinalData.add(O3rSO2_1Data);
							}

							String O3rSO2_2 = dev.getO3rSO2_2();
							if (!BasicUtils.isEmpty(O3rSO2_2)) {
								List<Object> O3rSO2_2Data = new ArrayList<>();
								float xdata = Float.parseFloat(O3rSO2_2);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//							          Timestamp time = dev.getStarttime();
								//							          Timestamp offsetTime = new Timestamp(time.getTime());
								O3rSO2_2Data.add(time.getTime());
								O3rSO2_2Data.add(xdata);
								o3rSO2_2FinalData.add(O3rSO2_2Data);
							}

							previousTime = currentTime;
						}
					}
				}
			}else {
				// Second Wise
				int counter = 0;
				Integer counter1 = -1;
				List<Integer> apneaIndex = new ArrayList<Integer>();
				for (DeviceMonitorDetail dev : resultSet) {
					if (dev != null) {
						
						// iterate over vent.
						Timestamp time = dev.getStarttime();
						if (overDelay != 0) {
							Long newTime = time.getTime() + overDelay;
							time = new Timestamp(newTime);
						}

						currentTime = dev.getStarttime().toString().substring(0, 19);
						if (!currentTime.equalsIgnoreCase(previousTime)) {
							
							
							Integer respiratoryScore = 0;
							Integer cardiovascularScore = 0;
							Integer hematologicScore = 0;
							
							for(List<Object> plateletCountMapObject : plateletCountMapFinal) {
								Timestamp labReportDate = (Timestamp) plateletCountMapObject.get(0);
								Integer evaluation = (Integer) plateletCountMapObject.get(1);
								
								if(dev.getStarttime().getTime() >= labReportDate.getTime()) {
									hematologicScore = evaluation;
								}			
							}
							
							List<String> prescriptionList = new ArrayList<String>();
							Integer isInotrope = 0;

							for(List<Object> prescriptionListObject : prescriptionListObjectFinal) {
								Timestamp startDateCardio = (Timestamp) prescriptionListObject.get(0);
								Timestamp endDateCardio = (Timestamp) prescriptionListObject.get(1);
								String medicineNameCardio = (String) prescriptionListObject.get(2);
								
								if(dev.getStarttime().getTime() >= startDateCardio.getTime() && dev.getStarttime().getTime() <= endDateCardio.getTime() && !prescriptionList.contains(medicineNameCardio)) {
									isInotrope = 1;
									prescriptionList.add(medicineNameCardio);
								}
								if(prescriptionList.size() == 1) {
									cardiovascularScore = 2;
								}else if(prescriptionList.size() == 2) {
									cardiovascularScore = 4;
								}
										
							}
							
							
							Integer fio2 = -1;

							for(Timestamp entryDate : ventilationCountMap.keySet()) {								
								if(dev.getStarttime().getTime() >= entryDate.getTime() && ((dev.getStarttime().getTime() - entryDate.getTime()) <= (2*60*60*1000))) {
									fio2 = ventilationCountMap.get(entryDate);
								}
							}
							
							float respFactor = 4;
							counter1++;
							
							String hr = dev.getHeartrate();
							if (!BasicUtils.isEmpty(hr)) {
								List<Object> hrData = new ArrayList<>();
								float xdata = Float.parseFloat(hr);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//							          Timestamp time = dev.getStarttime();
								//							          Timestamp offsetTime = new Timestamp(time.getTime());
								hrData.add(time.getTime());
								hrData.add(xdata);
								hrFinalData.add(hrData);
								hryAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								hrxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 8);
								dateTimeStrTemp = dateTimeStrTemp + "";
								allxAxisData.add(dateTimeStr);
							}
							
							String spo2 = dev.getSpo2();
							if (!BasicUtils.isEmpty(spo2)) {
								List<Object> spo2Data = new ArrayList<>();
								float xdata = Float.parseFloat(spo2);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								sPO2yAxisData.add(xdata);
								//							          Timestamp time = dev.getStarttime();
								//							          Timestamp offsetTime = new Timestamp(time.getTime());
								spo2Data.add(time.getTime());
								spo2Data.add(xdata);
								spo2FinalData.add(spo2Data);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								sPO2xAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 8);
								dateTimeStrTemp = dateTimeStrTemp + "";
								allxAxisData.add(dateTimeStr);
								respFactor = xdata/fio2;
								
								if (!BasicUtils.isEmpty(hr) && xdata <= 80 && Float.parseFloat(hr) <= 100) {
									
									List<Object> eventData = new ArrayList<>();
									eventyAxisData.add(1f);
									
									eventData.add(time.getTime());
									eventData.add(1f);
									eventFinalData.add(eventData);
									
									dateTime = time;
									dateTimeStr = CalendarUtility
											.getTimeStampFormat(
													CalendarUtility.CLIENT_CRUD_OPERATION)
											.format(dateTime);
									dateTimeStr = dateTimeStr.substring(11, 19);
									eventxAxisData.add(dateTimeStr);
									apneaIndex.add(counter1);
									counter++;

								}else {
									List<Object> eventData = new ArrayList<>();
									eventyAxisData.add(0f);
									
									eventData.add(time.getTime());
									eventData.add(0f);
									eventFinalData.add(eventData);
									
									dateTime = time;
									dateTimeStr = CalendarUtility
											.getTimeStampFormat(
													CalendarUtility.CLIENT_CRUD_OPERATION)
											.format(dateTime);
									dateTimeStr = dateTimeStr.substring(11, 19);
									eventxAxisData.add(dateTimeStr);
									
									if(counter > 0 && counter < 15) {
										
										for(Integer count : apneaIndex) {
											
											
											eventFinalData.get(count).set(1, 0f);

										}
										apneaIndex = new ArrayList<Integer>();
										counter = 0;
										
									}else if(counter >= 15) {
										apneaIndex = new ArrayList<Integer>();
										counter = 0;
									}
								}
							}else {
								List<Object> eventData = new ArrayList<>();
								eventyAxisData.add(0f);
								
								eventData.add(time.getTime());
								eventData.add(0f);
								eventFinalData.add(eventData);
								
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								eventxAxisData.add(dateTimeStr);
								if(counter > 0 && counter < 15) {
									
									for(Integer count : apneaIndex) {
										
										eventFinalData.get(count).set(1, 0f);

									}
									apneaIndex = new ArrayList<Integer>();
									counter = 0;
									
								}else if(counter >= 15) {
									apneaIndex = new ArrayList<Integer>();
									counter = 0;
								}
							}
							
							if(fio2 != -1) {

								if(respFactor >= 3) {
									respiratoryScore = 0;
								}else if(respFactor < 3 && respFactor >= 2) {
									respiratoryScore = 2;
								}else if(respFactor < 2 && respFactor >= 1.5) {
									respiratoryScore = 4;
								}else if(respFactor < 1.5 && respFactor >= 1) {
									respiratoryScore = 6;
								}else {
									respiratoryScore = 8;
								}
							}else {
								respFactor = 4;
							}
							
							
							Integer totalnSofaScore = respiratoryScore + cardiovascularScore + hematologicScore;
							List<Object> nSofaData = new ArrayList<>();
							
							nSofaData.add(time.getTime());
							nSofaData.add(totalnSofaScore);
							nSofaFinalData.add(nSofaData);
							
							List<Object> inotropeData = new ArrayList<>();
							inotropeData.add(time.getTime());
							inotropeData.add(isInotrope);
							inotropeFinalData.add(inotropeData);
							
							List<Object> spo2Fio2RatioData = new ArrayList<>();
							spo2Fio2RatioData.add(time.getTime());
							spo2Fio2RatioData.add(respFactor);
							spo2Fio2RatioFinalData.add(spo2Fio2RatioData);		
							
							// nbp_s
							String nbp_s = dev.getNbp_s();
							if (!BasicUtils.isEmpty(nbp_s)) {
								List<Object> nbp_sData = new ArrayList<>();
								float xdata = Float.parseFloat(nbp_s);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								nbp_sData.add(time.getTime());
								nbp_sData.add(xdata);
								nbpSFinalData.add(nbp_sData);
								nbpSyAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								nbpSxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 8);
								dateTimeStrTemp = dateTimeStrTemp + "";
								allxAxisData.add(dateTimeStr);
							}

							// nbp_d
							String nbp_d = dev.getNbp_d();
							if (!BasicUtils.isEmpty(nbp_d)) {
								List<Object> nbp_dData = new ArrayList<>();
								float xdata = Float.parseFloat(nbp_d);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//										  Timestamp time = dev.getStarttime();
								//										  Timestamp offsetTime = new Timestamp(time.getTime());
								nbp_dData.add(time.getTime());
								nbp_dData.add(xdata);
								nbpDFinalData.add(nbp_dData);
								nbpDyAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								nbpDxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 8);
								dateTimeStrTemp = dateTimeStrTemp + "";
								allxAxisData.add(dateTimeStr);
							}

							// nbp_m
							String nbp_m = dev.getNbp_m();
							if (!BasicUtils.isEmpty(nbp_m)) {
								List<Object> nbp_mData = new ArrayList<>();
								float xdata = Float.parseFloat(nbp_m);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//										  Timestamp time = dev.getStarttime();
								//										  Timestamp offsetTime = new Timestamp(time.getTime() );
								nbp_mData.add(time.getTime());
								nbp_mData.add(xdata);
								nbpMFinalData.add(nbp_mData);
								nbpMyAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								nbpMxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 8);
								dateTimeStrTemp = dateTimeStrTemp + "";
								allxAxisData.add(dateTimeStr);
							}

							// ibp_s
							String ibp_s = dev.getIbp_s();
							if (!BasicUtils.isEmpty(ibp_s)) {
								List<Object> ibp_sData = new ArrayList<>();
								float xdata = Float.parseFloat(ibp_s);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//										  Timestamp time = dev.getStarttime();
								//										  Timestamp offsetTime = new Timestamp(time.getTime() );
								ibp_sData.add(time.getTime());
								ibp_sData.add(xdata);
								ibpSFinalData.add(ibp_sData);
								ibpSyAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								ibpSxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 8);
								dateTimeStrTemp = dateTimeStrTemp + "";
								allxAxisData.add(dateTimeStr);
							}

							// ibp_d
							String ibp_d = dev.getIbp_d();
							if (!BasicUtils.isEmpty(ibp_d)) {
								List<Object> ibp_dData = new ArrayList<>();
								float xdata = Float.parseFloat(ibp_d);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//										  Timestamp time = dev.getStarttime();
								//										  Timestamp offsetTime = new Timestamp(time.getTime());
								ibp_dData.add(time.getTime());
								ibp_dData.add(xdata);
								ibpDFinalData.add(ibp_dData);
								ibpDyAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								ibpDxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 8);
								dateTimeStrTemp = dateTimeStrTemp + "";
								allxAxisData.add(dateTimeStr);
							}

							// ibp_m
							String ibp_m = dev.getIbp_m();
							if (!BasicUtils.isEmpty(ibp_m)) {
								List<Object> ibp_mData = new ArrayList<>();
								float xdata = Float.parseFloat(ibp_m);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//										  Timestamp time = dev.getStarttime();
								//										  Timestamp offsetTime = new Timestamp(time.getTime());
								ibp_mData.add(time.getTime());
								ibp_mData.add(xdata);
								ibpMFinalData.add(ibp_mData);
								ibpMyAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								ibpMxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 8);
								dateTimeStrTemp = dateTimeStrTemp + "";
								allxAxisData.add(dateTimeStr);
							}

							String rr = dev.getEcgResprate();
							if (!BasicUtils.isEmpty(rr)) {
								List<Object> rrData = new ArrayList<>();
								float xdata = Float.parseFloat(rr);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								rRyAxisData.add(xdata);
								//							          Timestamp time = dev.getStarttime();
								//							          Timestamp offsetTime = new Timestamp(time.getTime() );
								rrData.add(time.getTime());
								rrData.add(xdata);
								rrFinalData.add(rrData);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								rRxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 8);
								dateTimeStrTemp = dateTimeStrTemp + "";
								allxAxisData.add(dateTimeStr);
							}

							String pulse = dev.getPulserate();
							if (!BasicUtils.isEmpty(pulse)) {
								List<Object> pulseData = new ArrayList<>();
								float xdata = Float.parseFloat(pulse);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								pulseyAxisData.add(xdata);
								//							          Timestamp time = dev.getStarttime();
								//							          Timestamp offsetTime = new Timestamp(time.getTime() );
								pulseData.add(time.getTime());
								pulseData.add(xdata);
								pulseFinalData.add(pulseData);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								pulsexAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 8);
								dateTimeStrTemp = dateTimeStrTemp + "";
								allxAxisData.add(dateTimeStr);
							}

							String pi = dev.getPi();
							if (!BasicUtils.isEmpty(pi)) {
								List<Object> piData = new ArrayList<>();
								float xdata = Float.parseFloat(pi);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//							          Timestamp time = dev.getStarttime();
								//							          Timestamp offsetTime = new Timestamp(time.getTime());
								piData.add(time.getTime());
								piData.add(xdata);
								piFinalData.add(piData);
							}

							String O3rSO2_1 = dev.getO3rSO2_1();
							if (!BasicUtils.isEmpty(O3rSO2_1)) {
								List<Object> O3rSO2_1Data = new ArrayList<>();
								float xdata = Float.parseFloat(O3rSO2_1);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//							          Timestamp time = dev.getStarttime();
								//							          Timestamp offsetTime = new Timestamp(time.getTime());
								O3rSO2_1Data.add(time.getTime());
								O3rSO2_1Data.add(xdata);
								o3rSO2_1FinalData.add(O3rSO2_1Data);
							}

							String O3rSO2_2 = dev.getO3rSO2_2();
							if (!BasicUtils.isEmpty(O3rSO2_2)) {
								List<Object> O3rSO2_2Data = new ArrayList<>();
								float xdata = Float.parseFloat(O3rSO2_2);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//							          Timestamp time = dev.getStarttime();
								//							          Timestamp offsetTime = new Timestamp(time.getTime());
								O3rSO2_2Data.add(time.getTime());
								O3rSO2_2Data.add(xdata);
								o3rSO2_2FinalData.add(O3rSO2_2Data);
							}

							previousTime = currentTime;
						}
					}
				}
			}


			DeviceGraphAxisJson hrAxis = new DeviceGraphAxisJson();
			hrAxis.setxAxis(hrxAxisData);
			hrAxis.setyAxis(hryAxisData);

			DeviceGraphAxisJson spo2Axis = new DeviceGraphAxisJson();
			spo2Axis.setxAxis(sPO2xAxisData);
			spo2Axis.setyAxis(sPO2yAxisData);

			DeviceGraphAxisJson eventAxis = new DeviceGraphAxisJson();
			eventAxis.setxAxis(eventxAxisData);
			eventAxis.setyAxis(eventyAxisData);
			
			DeviceGraphAxisJson rrAxis = new DeviceGraphAxisJson();
			rrAxis.setxAxis(rRxAxisData);
			rrAxis.setyAxis(rRyAxisData);

			DeviceGraphAxisJson pulseAxis = new DeviceGraphAxisJson();
			pulseAxis.setxAxis(pulsexAxisData);
			pulseAxis.setyAxis(pulseyAxisData);

			DeviceGraphAxisJson nbpSAxis = new DeviceGraphAxisJson();
			nbpSAxis.setxAxis(nbpSxAxisData);
			nbpSAxis.setyAxis(nbpSyAxisData);

			DeviceGraphAxisJson nbpDAxis = new DeviceGraphAxisJson();
			nbpDAxis.setxAxis(nbpDxAxisData);
			nbpDAxis.setyAxis(nbpDyAxisData);

			DeviceGraphAxisJson nbpMAxis = new DeviceGraphAxisJson();
			nbpMAxis.setxAxis(nbpMxAxisData);
			nbpMAxis.setyAxis(nbpMyAxisData);

			DeviceGraphAxisJson ibpSAxis = new DeviceGraphAxisJson();
			ibpSAxis.setxAxis(ibpSxAxisData);
			ibpSAxis.setyAxis(ibpSyAxisData);

			DeviceGraphAxisJson ibpDAxis = new DeviceGraphAxisJson();
			ibpDAxis.setxAxis(ibpDxAxisData);
			ibpDAxis.setyAxis(ibpDyAxisData);

			DeviceGraphAxisJson ibpMAxis = new DeviceGraphAxisJson();
			ibpMAxis.setxAxis(ibpMxAxisData);
			ibpMAxis.setyAxis(ibpMyAxisData);

			// allxAxisDataSet.addAll(allxAxisData);

			obj.setHR(hrAxis);
			obj.setEvent(eventAxis);

			obj.setRR(rrAxis);
			obj.setSPO2(spo2Axis);
			obj.setPR(pulseAxis);

			obj.setNbpS(nbpSAxis);
			obj.setNbpD(nbpDAxis);
			obj.setNbpM(nbpMAxis);

			obj.setIbpS(ibpSAxis);
			obj.setIbpD(ibpDAxis);
			obj.setIbpM(ibpMAxis);

			obj.setHR(hrAxis);
			obj.setRR(rrAxis);
			obj.setSPO2(spo2Axis);
			obj.setPR(pulseAxis);
			obj.setUhid(uhid);

			obj.setNbpSObject(nbpSFinalData);
			obj.setNbpDObject(nbpDFinalData);
			obj.setNbpMObject(nbpMFinalData);

			obj.setIbpSObject(ibpSFinalData);
			obj.setIbpDObject(ibpDFinalData);
			obj.setIbpMObject(ibpMFinalData);

			obj.setHRObject(hrFinalData);
			obj.setEventObject(eventFinalData);

			obj.setRRObject(rrFinalData);
			obj.setnSofaObject(nSofaFinalData);
			obj.setInotropeObject(inotropeFinalData);
			obj.setSpo2fio2RatioObject(spo2Fio2RatioFinalData);			
			obj.setSPO2Object(spo2FinalData);
			obj.setPRObject(pulseFinalData);
			obj.setPIObject(piFinalData);
			obj.setO3rSO2_1Object(o3rSO2_1FinalData);
			obj.setO3rSO2_2Object(o3rSO2_2FinalData);

			obj.setDaily("hourly");
		}
		return obj;
	}

	public DeviceGraphDataJson getVentilatorData(String uhid, String entryTimeServerFormat, String entryEndTimeServerFormat, int overDelay, DeviceGraphDataJson obj, String isHourly) {
		List<List<Object>> pipFinalData = new ArrayList<>();
		List<List<Object>> peepFinalData = new ArrayList<>();
		List<List<Object>> fio2FinalData = new ArrayList<>();
		List<List<Object>> mapFinalData = new ArrayList<>();

		List<List<Object>> flowRateFinalData = new ArrayList<>();

		String currentTime = "";
		String previousTime = "";
		Timestamp currentHourTime = null;
		Timestamp previousHourTime = null;
		boolean statusFlag = false;
		ArrayList<String> allxAxisData = new ArrayList<>();

		String query1 = "SELECT obj FROM DeviceVentilatorData as obj WHERE " + "uhid ='"
				+ uhid.trim() + "' " + "AND start_time >= '" + entryTimeServerFormat
				+ "' AND start_time <= '" + entryEndTimeServerFormat + "' "
				+ " order by start_time";

		List<DeviceVentilatorData> resultSet = patientDao.getListFromMappedObjNativeQuery(query1);

		// Ventilator Data
		if (!BasicUtils.isEmpty(resultSet)) {

			if (isHourly.equalsIgnoreCase("daily")) {
				for (DeviceVentilatorData dev : resultSet) {
					if (dev != null) {

						if (!statusFlag) {
							previousHourTime = new Timestamp(
									dev.getStart_time().getTime() - (5 * 60 * 60 * 1000));
							statusFlag = true;
						}

						currentHourTime = dev.getStart_time();
						currentTime = dev.getStart_time().toString().substring(0, 16);

						// iterate over vent.
						Timestamp time = dev.getStart_time();
						if (overDelay != 0) {
							Long newTime = time.getTime() + overDelay;
							time = new Timestamp(newTime);
						}

						if (!currentTime.equalsIgnoreCase(previousTime)
								&& ((currentHourTime.getTime()
								- previousHourTime.getTime()) > (60 * 60 * 1000))) {
							String pip = dev.getPip();
							if (!BasicUtils.isEmpty(pip)) {
								List<Object> pipData = new ArrayList<>();
								float xdata = Float.parseFloat(pip);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//													Timestamp time = dev.getStart_time();
								//													Timestamp offsetTime = new Timestamp(time.getTime());
								pipData.add(time.getTime());
								pipData.add(xdata);
								pipFinalData.add(pipData);
								//	                            hryAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								//	                            hrxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							String peep = dev.getPeep();
							if (!BasicUtils.isEmpty(peep)) {
								List<Object> peepData = new ArrayList<>();
								float xdata = Float.parseFloat(peep);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//	                            rRyAxisData.add(xdata);
								//													Timestamp time = dev.getStart_time();
								//													Timestamp offsetTime = new Timestamp(time.getTime());
								peepData.add(time.getTime());
								peepData.add(xdata);
								peepFinalData.add(peepData);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								//	                            rRxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							String fio2 = dev.getFio2();
							if (!BasicUtils.isEmpty(fio2)) {
								List<Object> fio2Data = new ArrayList<>();
								float xdata = Float.parseFloat(fio2);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//	                            pulseyAxisData.add(xdata);
								//													Timestamp time = dev.getStart_time();
								//													Timestamp offsetTime = new Timestamp(time.getTime());
								fio2Data.add(time.getTime());
								fio2Data.add(xdata);
								fio2FinalData.add(fio2Data);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								//	                            pulsexAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							String map = dev.getMap();
							if (!BasicUtils.isEmpty(map)) {
								List<Object> mapData = new ArrayList<>();
								float xdata = Float.parseFloat(map);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//	                            sPO2yAxisData.add(xdata);
								//													Timestamp time = dev.getStart_time();
								//													Timestamp offsetTime = new Timestamp(time.getTime());
								mapData.add(time.getTime());
								mapData.add(xdata);
								mapFinalData.add(mapData);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								//	                            sPO2xAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							String rate = dev.getRate();
							if (!BasicUtils.isEmpty(rate)) {
								List<Object> rateData = new ArrayList<>();
								float xdata = Float.parseFloat(rate);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//													Timestamp time = dev.getStart_time();
								//													Timestamp offsetTime = new Timestamp(time.getTime());
								rateData.add(time.getTime());
								rateData.add(xdata);
								flowRateFinalData.add(rateData);
							}

							previousTime = currentTime;
							previousHourTime = currentHourTime;
						}
					}
				}
			} else {
				LinkedHashMap<Timestamp, HashMap<String, List<Float>>> allValueMap = new LinkedHashMap<>();
				for (DeviceVentilatorData vent : resultSet) {
					// iterate over vent.
					Timestamp time = vent.getStart_time();
					if (overDelay != 0) {
						Long newTime = time.getTime() + overDelay;
						time = new Timestamp(newTime);
					}

					if (!BasicUtils.isEmpty(time)) {
						int hours = time.getHours();
						int minutes = time.getMinutes();

						String hourwithFormat = String.format("%02d", hours);
						String minWithFormat = String.format("%02d", minutes);

						String hrmin = hourwithFormat + ":" + minWithFormat;
						if (allValueMap.containsKey(time)) {
							HashMap<String, List<Float>> valueMap = allValueMap.get(time);

							if (!BasicUtils.isEmpty(vent.getPeep())) {
								if (valueMap.containsKey("PEEP")) {
									List<Float> listval = valueMap.get("PEEP");
									listval.add(Float.parseFloat(vent.getPeep()));
									valueMap.put("PEEP", listval);
								} else {
									List<Float> listval = new ArrayList<>();
									listval.add(Float.parseFloat(vent.getPeep()));
									valueMap.put("PEEP", listval);
								}
							}

							if (!BasicUtils.isEmpty(vent.getPip())) {
								if (valueMap.containsKey("PIP")) {
									List<Float> listval = valueMap.get("PIP");
									listval.add(Float.parseFloat(vent.getPip()));
									valueMap.put("PIP", listval);
								} else {
									List<Float> listval = new ArrayList<>();
									listval.add(Float.parseFloat(vent.getPip()));
									valueMap.put("PIP", listval);
								}
							}

							if (!BasicUtils.isEmpty(vent.getFio2())) {
								if (valueMap.containsKey("FIO2")) {
									List<Float> listval = valueMap.get("FIO2");
									listval.add(Float.parseFloat(vent.getFio2()));
									valueMap.put("FIO2", listval);
								} else {
									List<Float> listval = new ArrayList<>();
									listval.add(Float.parseFloat(vent.getFio2()));
									valueMap.put("FIO2", listval);
								}
							}

							if (!BasicUtils.isEmpty(vent.getMap())) {
								if (valueMap.containsKey("MAP")) {
									List<Float> listval = valueMap.get("MAP");
									listval.add(Float.parseFloat(vent.getMap()));
									valueMap.put("MAP", listval);
								} else {
									List<Float> listval = new ArrayList<>();
									listval.add(Float.parseFloat(vent.getMap()));
									valueMap.put("MAP", listval);
								}
							}

							if (!BasicUtils.isEmpty(vent.getRate())) {
								if (valueMap.containsKey("FLOWRATE")) {
									List<Float> listval = valueMap.get("FLOWRATE");
									listval.add(Float.parseFloat(vent.getRate()));
									valueMap.put("FLOWRATE", listval);
								} else {
									List<Float> listval = new ArrayList<>();
									listval.add(Float.parseFloat(vent.getRate()));
									valueMap.put("FLOWRATE", listval);
								}
							}

							allValueMap.put(time, valueMap);
						} else {
							HashMap<String, List<Float>> newValMap = new HashMap<>();
							if (!BasicUtils.isEmpty(vent.getPeep())) {
								List<Float> listval = new ArrayList<>();
								listval.add(Float.parseFloat(vent.getPeep()));
								newValMap.put("PEEP", listval);
							}
							if (!BasicUtils.isEmpty(vent.getPip())) {
								List<Float> listval = new ArrayList<>();
								listval.add(Float.parseFloat(vent.getPip()));
								newValMap.put("PIP", listval);
							}
							if (!BasicUtils.isEmpty(vent.getFio2())) {
								List<Float> listval = new ArrayList<>();
								listval.add(Float.parseFloat(vent.getFio2()));
								newValMap.put("FIO2", listval);
							}
							if (!BasicUtils.isEmpty(vent.getMap())) {
								List<Float> listval = new ArrayList<>();
								listval.add(Float.parseFloat(vent.getMap()));
								newValMap.put("MAP", listval);
							}
							if (!BasicUtils.isEmpty(vent.getRate())) {
								List<Float> listval = new ArrayList<>();
								listval.add(Float.parseFloat(vent.getRate()));
								newValMap.put("FLOWRATE", listval);
							}
							allValueMap.put(time, newValMap);
						}
					}
				}

				// calculate mean and set the values.
				if (!BasicUtils.isEmpty(allValueMap)) {
					// iterate map to calculate mean values
					// and push it to x and y axis.
					for (Timestamp key : allValueMap.keySet()) {
						HashMap<String, List<Float>> valMap = allValueMap.get(key);
						{
							List<Float> listval = valMap.get("PEEP");
							if (!BasicUtils.isEmpty(listval)) {
								List<Object> peepData = new ArrayList<>();
								Float val = calculateMeanFromList(listval);
								String xDate = key + ":00";
								allxAxisData.add(xDate);
								//										Timestamp offsetTime = new Timestamp(key.getTime());
								peepData.add(key.getTime());
								peepData.add(BasicUtils.round(val, 1));
								peepFinalData.add(peepData);
							}
						}
						{
							List<Float> listval = valMap.get("PIP");
							if (!BasicUtils.isEmpty(listval)) {
								List<Object> pipData = new ArrayList<>();
								Float val = calculateMeanFromList(listval);
								String xDate = key + ":00";
								allxAxisData.add(xDate);
								//							          Timestamp offsetTime = new Timestamp(key.getTime());
								pipData.add(key.getTime());
								pipData.add(BasicUtils.round(val, 1));
								pipFinalData.add(pipData);
							}

						}

						{
							List<Float> listval = valMap.get("FIO2");
							if (!BasicUtils.isEmpty(listval)) {
								List<Object> fio2Data = new ArrayList<>();
								Float val = calculateMeanFromList(listval);
								String xDate = key + ":00";
								allxAxisData.add(xDate);
								//							          Timestamp offsetTime = new Timestamp(key.getTime());
								fio2Data.add(key.getTime());
								fio2Data.add(BasicUtils.round(val, 1));
								fio2FinalData.add(fio2Data);
							}

						}

						{
							List<Float> listval = valMap.get("MAP");
							if (!BasicUtils.isEmpty(listval)) {
								List<Object> mapData = new ArrayList<>();
								Float val = calculateMeanFromList(listval);
								String xDate = key + ":00";
								allxAxisData.add(xDate);
								//							          Timestamp offsetTime = new Timestamp(key.getTime());
								mapData.add(key.getTime());
								mapData.add(BasicUtils.round(val, 1));
								mapFinalData.add(mapData);
							}

						}

						{
							List<Float> listval = valMap.get("FLOWRATE");
							if (!BasicUtils.isEmpty(listval)) {
								List<Object> flowRateData = new ArrayList<>();
								Float val = calculateMeanFromList(listval);
								String xDate = key + ":00";
								allxAxisData.add(xDate);
								//							          Timestamp offsetTime = new Timestamp(key.getTime());
								flowRateData.add(key.getTime());
								flowRateData.add(BasicUtils.round(val, 1));
								flowRateFinalData.add(flowRateData);
							}

						}
					}

					obj.setPEEPObject(peepFinalData);
					obj.setPIPObject(pipFinalData);
					obj.setFIO2Object(fio2FinalData);
					obj.setMAPObject(mapFinalData);
					obj.setFLOWRATEObject(flowRateFinalData);
				}
			}

			DeviceGraphAxisJson pipAxis = new DeviceGraphAxisJson();
			DeviceGraphAxisJson peepAxis = new DeviceGraphAxisJson();
			DeviceGraphAxisJson fio2Axis = new DeviceGraphAxisJson();
			DeviceGraphAxisJson mapAxis = new DeviceGraphAxisJson();
			DeviceGraphAxisJson flowRateAxis = new DeviceGraphAxisJson();
//			allxAxisDataSet.addAll(allxAxisData);

			obj.setPIP(pipAxis);
			obj.setPEEP(peepAxis);
			obj.setFIO2(fio2Axis);
			obj.setMAP(mapAxis);
			obj.setRATE(flowRateAxis);

			obj.setUhid(uhid);
			obj.setPIPObject(pipFinalData);
			obj.setPEEPObject(peepFinalData);
			obj.setFIO2Object(fio2FinalData);
			obj.setMAPObject(mapFinalData);
			obj.setFLOWRATEObject(flowRateFinalData);
			obj.setDaily("hourly");
		}

		return obj;
	}

	public DeviceGraphDataJson getIncubatorWarmerData(String uhid, String entryTimeServerFormat, String entryEndTimeServerFormat, int overDelay, DeviceGraphDataJson obj, String isHourly) {

		List<List<Object>> centralTempFinalData = new ArrayList<>();
		List<List<Object>> peripheralTempFinalData = new ArrayList<>();
		List<List<Object>> humidityFinalData = new ArrayList<>();

		List<String> centralTempxAxisData = new ArrayList<String>();
		List<Float> centralTempyAxisData = new ArrayList<Float>();

		List<String> peripheralTempxAxisData = new ArrayList<String>();
		List<Float> peripheralTempyAxisData = new ArrayList<Float>();

		List<String> humidityxAxisData = new ArrayList<String>();
		List<Float> humidityyAxisData = new ArrayList<Float>();

		// Logic for getting the temperature and humidity
		String currentTime = "";
		String previousTime = "";
		Timestamp currentHourTime = null;
		Timestamp previousHourTime = null;
		boolean statusFlag = false;
		ArrayList<String> allxAxisData = new ArrayList<>();

		String query3 = "SELECT obj FROM DeviceIncubatorWarmerDetail as obj WHERE " + "uhid ='"
				+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
				+ "' AND starttime <= '" + entryEndTimeServerFormat + "' "
				+ " order by starttime";

		List<DeviceIncubatorWarmerDetail> resultSet = patientDao.getListFromMappedObjNativeQuery(query3);

		// DeviceIncubatorWarmerDetail Data
		if (!BasicUtils.isEmpty(resultSet)) {
			if (isHourly.equalsIgnoreCase("daily")) {
				for (DeviceIncubatorWarmerDetail dev : resultSet) {
					if (dev != null) {

						if (!statusFlag) {
							previousHourTime = new Timestamp(
									dev.getStarttime().getTime() - (5 * 60 * 60 * 1000));
							statusFlag = true;
						}

						currentHourTime = dev.getStarttime();
						currentTime = dev.getStarttime().toString().substring(0, 16);
						Timestamp time = dev.getStarttime();

						if (overDelay != 0) {
							Long newTime = time.getTime() + overDelay;
							time = new Timestamp(newTime);
						}

						if (!currentTime.equalsIgnoreCase(previousTime)
								&& ((currentHourTime.getTime()
								- previousHourTime.getTime()) > (60 * 60 * 1000))) {

							// central temperature
							String centralTemperatureValue = getPeriTemperaturePriorityBased(dev);
							if (!BasicUtils.isEmpty(centralTemperatureValue)) {
								List<Object> centralTemp = new ArrayList<>();
								float xdata = Float.parseFloat(centralTemperatureValue);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								centralTemp.add(time.getTime());
								centralTemp.add(xdata);
								centralTempFinalData.add(centralTemp);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							// peripheral temperature
							String peripheralTemperatureValue = getPeriTemperaturePriorityBased(dev);
							if (!BasicUtils.isEmpty(peripheralTemperatureValue)) {
								List<Object> peripheralTemp = new ArrayList<>();
								float xdata = Float.parseFloat(peripheralTemperatureValue);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//													Timestamp time = dev.getStarttime();
								//													Timestamp offsetTime = new Timestamp(time.getTime());
								peripheralTemp.add(time.getTime());
								peripheralTemp.add(xdata);
								peripheralTempFinalData.add(peripheralTemp);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							// Humidity
							String humidityValue = dev.getHumidity();
							if (!BasicUtils.isEmpty(humidityValue)) {
								List<Object> humidityList = new ArrayList<>();
								float xdata = Float.parseFloat(humidityValue);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//													Timestamp time = dev.getStarttime();
								//													Timestamp offsetTime = new Timestamp(time.getTime());
								humidityList.add(time.getTime());
								humidityList.add(xdata);
								humidityFinalData.add(humidityList);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							previousTime = currentTime;
							previousHourTime = currentHourTime;
						}
					}
				}
			} else {
				for (DeviceIncubatorWarmerDetail dev : resultSet) {
					if (dev != null) {
						// iterate over vent.

						Timestamp time = dev.getStarttime();
						if (overDelay != 0) {
							Long newTime = time.getTime() + overDelay;
							time = new Timestamp(newTime);
						}

						currentTime = dev.getStarttime().toString().substring(0, 16);

						if (!currentTime.equalsIgnoreCase(previousTime)) {

							// central Temperature
							String c_temp = getPeriTemperaturePriorityBased(dev);
							if (!BasicUtils.isEmpty(c_temp)) {
								List<Object> c_tempList = new ArrayList<>();
								float xdata = Float.parseFloat(c_temp);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//													Timestamp time = dev.getStarttime();
								//													Timestamp offsetTime = new Timestamp(time.getTime() + 19800000);
								c_tempList.add(time.getTime());
								c_tempList.add(xdata);
								centralTempFinalData.add(c_tempList);
								centralTempyAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								centralTempxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							// peripheral Temperature
							String p_temp = getPeriTemperaturePriorityBased(dev);
							if (!BasicUtils.isEmpty(p_temp)) {
								List<Object> p_tempList = new ArrayList<>();
								float xdata = Float.parseFloat(p_temp);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//													Timestamp time = dev.getStarttime();
								//													Timestamp offsetTime = new Timestamp(time.getTime());
								p_tempList.add(time.getTime());
								p_tempList.add(xdata);
								peripheralTempFinalData.add(p_tempList);
								peripheralTempyAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								peripheralTempxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							// humidity
							String humidity_value = dev.getHumidity();
							if (!BasicUtils.isEmpty(humidity_value)) {
								List<Object> humidity_valueList = new ArrayList<>();
								float xdata = Float.parseFloat(humidity_value);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								//													Timestamp time = dev.getStarttime();
								//													Timestamp offsetTime = new Timestamp(time.getTime());
								humidity_valueList.add(time.getTime());
								humidity_valueList.add(xdata);
								humidityFinalData.add(humidity_valueList);
								humidityyAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								humidityxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}
							previousTime = currentTime;
						}
					}
				}
			}

			DeviceGraphAxisJson centralTemp = new DeviceGraphAxisJson();
			centralTemp.setxAxis(centralTempxAxisData);
			centralTemp.setyAxis(centralTempyAxisData);

			DeviceGraphAxisJson peripheralTemp = new DeviceGraphAxisJson();
			peripheralTemp.setxAxis(peripheralTempxAxisData);
			peripheralTemp.setyAxis(peripheralTempyAxisData);

			DeviceGraphAxisJson humidityAxis = new DeviceGraphAxisJson();
			humidityAxis.setxAxis(humidityxAxisData);
			humidityAxis.setyAxis(humidityyAxisData);

//			allxAxisDataSet.addAll(allxAxisData);

			obj.setUhid(uhid);

			obj.setcTemp(centralTemp);
			obj.setpTemp(peripheralTemp);
			obj.setHumidity(humidityAxis);

			obj.setCentralTempObject(centralTempFinalData);
			obj.setPeripheralTempObject(peripheralTempFinalData);
			obj.setHumidityObject(humidityFinalData);
			obj.setDaily("hourly");
		}

		return obj;
	}

	public DeviceGraphDataJson getDeviceCerebralOximeterData(String uhid, String entryTimeServerFormat, String entryEndTimeServerFormat, int overDelay, DeviceGraphDataJson obj, String isHourly) {
		String query2 = "SELECT obj FROM DeviceCerebralOximeterDetail as obj WHERE " + "uhid ='"
				+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
				+ "' AND starttime <= '" + entryEndTimeServerFormat + "' "
				+ " order by starttime";

		List<DeviceCerebralOximeterDetail> resultSet = patientDao.getListFromMappedObjNativeQuery(query2);

		List<List<Object>> channel1FinalData = new ArrayList<>();
		List<String> channel1xAxisData = new ArrayList<String>();
		List<Float> channel1yAxisData = new ArrayList<Float>();

		List<List<Object>> channel2FinalData = new ArrayList<>();
		List<String> channel2xAxisData = new ArrayList<String>();
		List<Float> channel2yAxisData = new ArrayList<Float>();

		List<List<Object>> channel3FinalData = new ArrayList<>();
		List<String> channel3xAxisData = new ArrayList<String>();
		List<Float> channel3yAxisData = new ArrayList<Float>();

		List<List<Object>> channel4FinalData = new ArrayList<>();
		List<String> channel4xAxisData = new ArrayList<String>();
		List<Float> channel4yAxisData = new ArrayList<Float>();

		DeviceGraphAxisJson channel1Axis = new DeviceGraphAxisJson();
		DeviceGraphAxisJson channel2Axis = new DeviceGraphAxisJson();
		DeviceGraphAxisJson channel3Axis = new DeviceGraphAxisJson();
		DeviceGraphAxisJson channel4Axis = new DeviceGraphAxisJson();

		String currentTime = "";
		String previousTime = "";
		Timestamp currentHourTime = null;
		Timestamp previousHourTime = null;
		boolean statusFlag = false;
		ArrayList<String> allxAxisData = new ArrayList<>();

		if (!BasicUtils.isEmpty(resultSet)) {

			if (isHourly.equalsIgnoreCase("daily")) {
				for (DeviceCerebralOximeterDetail dev : resultSet) {
					if (dev != null) {
						if (!statusFlag) {
							previousHourTime = new Timestamp(
									dev.getStarttime().getTime() - (5 * 60 * 60 * 1000));
							statusFlag = true;
						}

						Timestamp time = dev.getStarttime();

						if (overDelay != 0) {
							Long newTime = time.getTime() + overDelay;
							time = new Timestamp(newTime);
						}

						currentHourTime = dev.getStarttime();
						currentTime = dev.getStarttime().toString().substring(0, 16);

						if (!currentTime.equalsIgnoreCase(previousTime) && ((currentHourTime.getTime() - previousHourTime.getTime()) > (60 * 60 * 1000))) {
							String channel1 = dev.getChannel1_rso2();
							if (!BasicUtils.isEmpty(channel1)) {
								List<Object> channel1Data = new ArrayList<>();
								float xdata = Float.parseFloat(channel1);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								channel1Data.add(time);
								channel1Data.add(xdata);
								channel1FinalData.add(channel1Data);
								//	                            hryAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								//	                            hrxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							String channel2 = dev.getChannel2_rso2();
							if (!BasicUtils.isEmpty(channel2)) {
								List<Object> channel2Data = new ArrayList<>();
								float xdata = Float.parseFloat(channel2);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								channel2Data.add(time);
								channel2Data.add(xdata);
								channel2FinalData.add(channel2Data);
								//	                            hryAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								//	                            hrxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							String channel3 = dev.getChannel3_rso2();
							if (!BasicUtils.isEmpty(channel3)) {
								List<Object> channel3Data = new ArrayList<>();
								float xdata = Float.parseFloat(channel3);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								channel3Data.add(time);
								channel3Data.add(xdata);
								channel3FinalData.add(channel3Data);
								//	                            hryAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								//	                            hrxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							String channel4 = dev.getChannel4_rso2();
							if (!BasicUtils.isEmpty(channel4)) {
								List<Object> channel4Data = new ArrayList<>();
								float xdata = Float.parseFloat(channel4);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								channel4Data.add(time);
								channel4Data.add(xdata);
								channel4FinalData.add(channel4Data);
								//	                            hryAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								//	                            hrxAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							previousTime = currentTime;
							previousHourTime = currentHourTime;
						}
					}
				}
			} else {
				for (DeviceCerebralOximeterDetail dev : resultSet) {
					if (dev != null) {
						currentTime = dev.getStarttime().toString().substring(0, 16);

						Timestamp time = dev.getStarttime();

						if (overDelay != 0) {
							Long newTime = time.getTime() + overDelay;
							time = new Timestamp(newTime);
						}

						if (!currentTime.equalsIgnoreCase(previousTime)) {

							String channel1 = dev.getChannel1_rso2();
							if (!BasicUtils.isEmpty(channel1)) {
								List<Object> channel1Data = new ArrayList<>();
								float xdata = Float.parseFloat(channel1);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								channel1Data.add(time);
								channel1Data.add(xdata);
								channel1FinalData.add(channel1Data);
								channel1yAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								channel1xAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							String channel2 = dev.getChannel2_rso2();
							if (!BasicUtils.isEmpty(channel2)) {
								List<Object> channel2Data = new ArrayList<>();
								float xdata = Float.parseFloat(channel2);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								channel2Data.add(time);
								channel2Data.add(xdata);
								channel2FinalData.add(channel2Data);
								channel2yAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								channel2xAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							String channel3 = dev.getChannel3_rso2();
							if (!BasicUtils.isEmpty(channel3)) {
								List<Object> channel3Data = new ArrayList<>();
								float xdata = Float.parseFloat(channel3);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								channel3Data.add(time);
								channel3Data.add(xdata);
								channel3FinalData.add(channel3Data);
								channel3yAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								channel3xAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}

							String channel4 = dev.getChannel4_rso2();
							if (!BasicUtils.isEmpty(channel4)) {
								List<Object> channel4Data = new ArrayList<>();
								float xdata = Float.parseFloat(channel4);
								xdata = (float) (Math.round(Float.valueOf(xdata) * 10.0)
										/ 10.0);
								channel4Data.add(time);
								channel4Data.add(xdata);
								channel4FinalData.add(channel4Data);
								channel4yAxisData.add(xdata);
								Date dateTime = time;
								String dateTimeStr = CalendarUtility
										.getTimeStampFormat(
												CalendarUtility.CLIENT_CRUD_OPERATION)
										.format(dateTime);
								dateTimeStr = dateTimeStr.substring(11, 19);
								channel4xAxisData.add(dateTimeStr);
								String dateTimeStrTemp = dateTimeStr.substring(0, 6);
								dateTimeStrTemp = dateTimeStrTemp + "00";
								allxAxisData.add(dateTimeStr);
							}
							previousTime = currentTime;
						}
					}
				}

				channel1Axis.setxAxis(channel1xAxisData);
				channel1Axis.setyAxis(channel1yAxisData);

				channel2Axis.setxAxis(channel2xAxisData);
				channel2Axis.setyAxis(channel2yAxisData);

				channel3Axis.setxAxis(channel3xAxisData);
				channel3Axis.setyAxis(channel3yAxisData);

				channel4Axis.setxAxis(channel4xAxisData);
				channel4Axis.setyAxis(channel4yAxisData);
			}

//			allxAxisDataSet.addAll(allxAxisData);
			obj.setChannel1Rso2(channel1Axis);
			obj.setChannel2Rso2(channel2Axis);
			obj.setChannel3Rso2(channel3Axis);
			obj.setChannel4Rso2(channel4Axis);

			obj.setUhid(uhid);
			obj.setChannel1Rso2Object(channel1FinalData);
			obj.setChannel2Rso2Object(channel2FinalData);
			obj.setChannel3Rso2Object(channel3FinalData);
			obj.setChannel4Rso2Object(channel4FinalData);
			obj.setDaily("hourly");
		}
		return obj;
	}

	@SuppressWarnings({"unchecked", "deprecation"})
	@Override
	public DeviceGraphDataJson getDevGraphData(DeviceGraphDataJson graphData, String entryDate, String endDate) {
		// TODO Auto-generated method stub
		/*
		 * TimeZone.setDefault(TimeZone.getTimeZone(CalendarUtility.
		 * INDIA_TIME_ZONE_ID));
		 */

		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset() - TimeZone.getDefault().getRawOffset();

		System.out.println("Device Graph Data Client Time Zone Offset Value:" + TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset());
		System.out.println("Device Graph Data Default Time Zone Offset Value:" + TimeZone.getDefault().getRawOffset());

		if (TimeZone.getDefault().getRawOffset() == 0) {
			offset = 0;
		}

		System.out.println("Device Graph Data Offset Value:" + offset);
		DeviceGraphDataJson obj = new DeviceGraphDataJson();
		obj.setRange(getMinMaxRange());

		if (!entryDate.equalsIgnoreCase("undefined")) {
			if (!BasicUtils.isEmpty(graphData)) {
				String uhid = graphData.getUhid();
				String isDaily = graphData.isDaily();

				try {
					Date entryTimeStamp = CalendarUtility.getDateFormatGMT(CalendarUtility.CLIENT_CRUD_OPERATION)
							.parse(entryDate);
					Date entryEndTimeStamp = CalendarUtility.getDateFormatGMT(CalendarUtility.CLIENT_CRUD_OPERATION)
							.parse(endDate);

					List<Timestamp> fromHmTime = new ArrayList<Timestamp>();
					List<Timestamp> toHmTime = new ArrayList<Timestamp>();

					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
					String entryTimeServerFormat = formatter.format(entryTimeStamp);
					String entryEndTimeServerFormat = formatter.format(entryEndTimeStamp);

					if (!BasicUtils.isEmpty(entryDate) && !BasicUtils.isEmpty(endDate)) {

						TreeMap<Timestamp, List<Object>> hmDetails1 = new TreeMap<Timestamp, List<Object>>();
						TreeMap<Timestamp, List<Timestamp>> hmDetails = new TreeMap<Timestamp, List<Timestamp>>();
						List<List<Object>> hmlmDataFinal = new ArrayList<List<Object>>();
						List<List<Object>> hmlmDataFinalSet = new ArrayList<List<Object>>();

						if (graphData.isHmOutput()) {

							obj.setHmOutput(graphData.isHmOutput());
							// Blood Culture and NEC

							Timestamp fromNECTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							Timestamp toNECTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							boolean isNECStatus = false;

							Timestamp fromBloodCultureTime = new Timestamp(
									entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							Timestamp toBloodCultureTime = new Timestamp(
									entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							boolean isBloodCultureStatus = false;
							int sepsisStatus = -1;
							List<SaSepsis> sepsisSet = new ArrayList<>();
							String sepsisAssessmentQuery = "SELECT obj FROM SaSepsis as obj where uhid='" + uhid.trim()
									+ "' and assessment_time <= '" + entryTimeServerFormat
									+ "' order by assessment_time desc";
							List<SaSepsis> sepsisSetTemp = patientDao
									.getListFromMappedObjNativeQuery(sepsisAssessmentQuery);
							if (!BasicUtils.isEmpty(sepsisSetTemp)) {
								if (sepsisSetTemp.get(0).getEventstatus().equalsIgnoreCase("yes")) {
									sepsisStatus = 1;
								} else {
									sepsisStatus = 0;
								}
							}

							sepsisAssessmentQuery = "SELECT obj FROM SaSepsis as obj where uhid='" + uhid.trim()
									+ "' and assessment_time >= '" + entryTimeServerFormat
									+ "' and assessment_time <= '" + entryEndTimeServerFormat
									+ "' order by assessment_time";
							sepsisSetTemp = patientDao.getListFromMappedObjNativeQuery(sepsisAssessmentQuery);
							if (!BasicUtils.isEmpty(sepsisSetTemp))
								sepsisSet.addAll(sepsisSetTemp);

							if (!BasicUtils.isEmpty(sepsisSet)) {
								for (SaSepsis sasepsisObj : sepsisSet) {
									if (!(sasepsisObj.getEventstatus().equalsIgnoreCase("yes")) && sepsisStatus == 1) {
										sepsisStatus = 0;
										if (isNECStatus) {
											toNECTime = new Timestamp(sasepsisObj.getAssessmentTime().getTime());
											isNECStatus = false;
											fromHmTime.add(new Timestamp(fromNECTime.getTime()));
											toHmTime.add(new Timestamp(toNECTime.getTime()));
											List<Timestamp> objList = new ArrayList<Timestamp>();
											objList.add(toNECTime);
											hmDetails.put(fromNECTime, objList);
											List<Object> objList1 = new ArrayList<Object>();
											objList1.add(toNECTime);
											objList1.add("NEC");
											hmDetails1.put(fromNECTime, objList1);
										}
										if (isBloodCultureStatus) {
											toBloodCultureTime = new Timestamp(
													sasepsisObj.getAssessmentTime().getTime());
											isBloodCultureStatus = false;
											fromHmTime.add(new Timestamp(fromBloodCultureTime.getTime()));
											toHmTime.add(new Timestamp(toBloodCultureTime.getTime()));
											List<Timestamp> objList = new ArrayList<Timestamp>();
											objList.add(toBloodCultureTime);
											hmDetails.put(fromBloodCultureTime, objList);
											List<Object> objList1 = new ArrayList<Object>();
											objList1.add(toBloodCultureTime);
											objList1.add("Blood Culture Positive");
											hmDetails1.put(fromBloodCultureTime, objList1);
										}

									} else if ((sasepsisObj.getEventstatus().equalsIgnoreCase("yes"))
											&& (sepsisStatus == 0 || sepsisStatus == -1)) {
										sepsisStatus = 1;
									}
									if (!BasicUtils.isEmpty(sasepsisObj.getNecStatus()) && sasepsisObj.getNecStatus()
											&& (sasepsisObj.getEventstatus().equalsIgnoreCase("yes"))) {
										fromNECTime = new Timestamp(sasepsisObj.getAssessmentTime().getTime());
										isNECStatus = true;

									}
									if (!BasicUtils.isEmpty(sasepsisObj.getBloodCultureStatus())
											&& sasepsisObj.getBloodCultureStatus().equalsIgnoreCase("Positive")
											&& (sasepsisObj.getEventstatus().equalsIgnoreCase("yes"))) {
										fromBloodCultureTime = new Timestamp(sasepsisObj.getAssessmentTime().getTime());
										isBloodCultureStatus = true;
									}

								}
							}
							if (isNECStatus) {
								toNECTime = new Timestamp(entryEndTimeStamp.getTime());
								isNECStatus = false;
								fromHmTime.add(new Timestamp(fromNECTime.getTime()));
								toHmTime.add(new Timestamp(toNECTime.getTime()));
								List<Timestamp> objList = new ArrayList<Timestamp>();
								objList.add(toNECTime);
								hmDetails.put(fromNECTime, objList);
								List<Object> objList1 = new ArrayList<Object>();
								objList1.add(toNECTime);
								objList1.add("NEC");
								hmDetails1.put(fromNECTime, objList1);
							}
							if (isBloodCultureStatus) {
								toBloodCultureTime = new Timestamp(entryEndTimeStamp.getTime());
								isBloodCultureStatus = false;
								fromHmTime.add(new Timestamp(fromBloodCultureTime.getTime()));
								toHmTime.add(new Timestamp(toBloodCultureTime.getTime()));
								List<Timestamp> objList = new ArrayList<Timestamp>();
								objList.add(toBloodCultureTime);
								hmDetails.put(fromBloodCultureTime, objList);
								List<Object> objList1 = new ArrayList<Object>();
								objList1.add(toBloodCultureTime);
								objList1.add("Blood Culture Positive");
								hmDetails1.put(fromBloodCultureTime, objList1);
							}


							// Surfactant and Ventilation
							Timestamp fromSurfactantTime = new Timestamp(
									entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							Timestamp toSurfactantTime = new Timestamp(
									entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							boolean isSurfactantStatus = false;

							Timestamp fromVentilationTime = new Timestamp(
									entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							Timestamp toVentilationTime = new Timestamp(
									entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							boolean isVentilationStatus = false;

							String respSupportQuery = "SELECT obj FROM RespSupport as obj where uhid='" + uhid.trim()
									+ "' order by creationtime";
							List<RespSupport> ventSetTemp = patientDao
									.getListFromMappedObjNativeQuery(respSupportQuery);
							if (!BasicUtils.isEmpty(ventSetTemp)) {
								for (RespSupport supportObj : ventSetTemp) {
									if (!isVentilationStatus && supportObj.getIsactive()
											&& !BasicUtils.isEmpty(supportObj.getRsVentType())
											&& (supportObj.getRsVentType().equalsIgnoreCase("Mechanical Ventilation")
											|| supportObj.getRsVentType().equalsIgnoreCase("HFO"))) {
										fromVentilationTime = new Timestamp(supportObj.getCreationtime().getTime());
										isVentilationStatus = true;
									} else if (isVentilationStatus && (!supportObj.getIsactive()
											|| ((!BasicUtils.isEmpty(supportObj.getRsVentType()))
											&& (supportObj.getRsVentType().equalsIgnoreCase("CPAP") || supportObj.getRsVentType().equalsIgnoreCase("Low Flow O2") || supportObj.getRsVentType().equalsIgnoreCase("NIMV")
											|| supportObj.getRsVentType().equalsIgnoreCase("High Flow O2")))
											|| BasicUtils.isEmpty(supportObj.getRsVentType()))) {
										toVentilationTime = new Timestamp(supportObj.getCreationtime().getTime());
										isVentilationStatus = false;
										fromHmTime.add(new Timestamp(fromVentilationTime.getTime()));
										toHmTime.add(new Timestamp(toVentilationTime.getTime()));
										List<Timestamp> objList = new ArrayList<Timestamp>();
										objList.add(toVentilationTime);
										hmDetails.put(fromVentilationTime, objList);
										List<Object> objList1 = new ArrayList<Object>();
										objList1.add(toVentilationTime);
										objList1.add("Invasive Ventilation");
										hmDetails1.put(fromVentilationTime, objList1);
									}
								}
							}

							if (isVentilationStatus) {
								toVentilationTime = new Timestamp(entryEndTimeStamp.getTime());
								isVentilationStatus = false;
								fromHmTime.add(new Timestamp(fromVentilationTime.getTime()));
								toHmTime.add(new Timestamp(toVentilationTime.getTime()));
								List<Timestamp> objList = new ArrayList<Timestamp>();
								objList.add(toVentilationTime);
								hmDetails.put(fromVentilationTime, objList);
								List<Object> objList1 = new ArrayList<Object>();
								objList1.add(toVentilationTime);
								objList1.add("Invasive Ventilation");
								hmDetails1.put(fromVentilationTime, objList1);
							}

							List<SaRespRds> surfactantSet = new ArrayList<>();
							int sufactantStatus = -1;
							String surfactantAssessmentQuery = "SELECT obj FROM SaRespRds as obj where uhid='"
									+ uhid.trim() + "' and assessment_time >= '" + entryTimeServerFormat
									+ "' and assessment_time <= '" + entryEndTimeServerFormat
									+ "' order by assessment_time";
							List<SaRespRds> surfactantSetTemp = patientDao
									.getListFromMappedObjNativeQuery(surfactantAssessmentQuery);
							if (!BasicUtils.isEmpty(surfactantSetTemp))
								surfactantSet.addAll(surfactantSetTemp);

							if (!BasicUtils.isEmpty(surfactantSet)) {
								for (SaRespRds rdsObject : surfactantSet) {
									if (!(rdsObject.getEventstatus().equalsIgnoreCase("Yes")) && sufactantStatus == 1) {
										sufactantStatus = 0;
										if (isSurfactantStatus) {
											toSurfactantTime = new Timestamp(rdsObject.getAssessmentTime().getTime());
											isSurfactantStatus = false;
											fromHmTime.add(new Timestamp(fromSurfactantTime.getTime()));
											toHmTime.add(new Timestamp(toSurfactantTime.getTime()));
											List<Timestamp> objList = new ArrayList<Timestamp>();
											objList.add(toSurfactantTime);
											hmDetails.put(fromSurfactantTime, objList);
											List<Object> objList1 = new ArrayList<Object>();
											objList1.add(toSurfactantTime);
											objList1.add("Surfactant");
											hmDetails1.put(fromSurfactantTime, objList1);
										}

									} else if ((rdsObject.getEventstatus().equalsIgnoreCase("Yes"))
											&& (sufactantStatus == 0 || sufactantStatus == -1)) {
										sufactantStatus = 1;
									}
									if ((rdsObject.getEventstatus().equalsIgnoreCase("Yes"))
											&& !BasicUtils.isEmpty(rdsObject.getSufactantname())) {
										fromSurfactantTime = new Timestamp(rdsObject.getAssessmentTime().getTime());
										isSurfactantStatus = true;
									}

								}
							}
							if (isSurfactantStatus) {
								toSurfactantTime = new Timestamp(entryEndTimeStamp.getTime());
								isSurfactantStatus = false;
								fromHmTime.add(new Timestamp(fromSurfactantTime.getTime()));
								toHmTime.add(new Timestamp(toSurfactantTime.getTime()));
								List<Timestamp> objList = new ArrayList<Timestamp>();
								objList.add(toSurfactantTime);
								hmDetails.put(fromSurfactantTime, objList);
								List<Object> objList1 = new ArrayList<Object>();
								objList1.add(toSurfactantTime);
								objList1.add("Surfactant");
								hmDetails1.put(fromSurfactantTime, objList1);
							}

							// ROP
							String ropAssessmentQuery = "SELECT obj FROM ScreenRop as obj where uhid='" + uhid.trim()
									+ "' and screening_time >= '" + entryTimeServerFormat + "' and screening_time <= '"
									+ entryEndTimeServerFormat + "' order by screening_time";
							List<ScreenRop> ropSet = patientDao.getListFromMappedObjNativeQuery(ropAssessmentQuery);

							if (!BasicUtils.isEmpty(ropSet)) {
								for (ScreenRop ropObj : ropSet) {
									if (!BasicUtils.isEmpty(ropObj.getRop_left_stage())
											&& ((ropObj.getRop_left_stage().equalsIgnoreCase("II"))
											|| (ropObj.getRop_left_stage().equalsIgnoreCase("III"))
											|| (ropObj.getRop_left_stage().equalsIgnoreCase("IV"))
											|| (ropObj.getRop_left_stage().equalsIgnoreCase("V")))
											|| (!BasicUtils.isEmpty(ropObj.getRop_right_stage())
											&& ((ropObj.getRop_right_stage().equalsIgnoreCase("II"))
											|| (ropObj.getRop_right_stage().equalsIgnoreCase("III"))
											|| (ropObj.getRop_right_stage().equalsIgnoreCase("IV"))
											|| (ropObj.getRop_right_stage().equalsIgnoreCase("V"))))) {
										List<Timestamp> objList = new ArrayList<Timestamp>();
										fromHmTime.add(new Timestamp(ropObj.getScreening_time().getTime()));
										toHmTime.add(new Timestamp(entryEndTimeStamp.getTime()));
										Timestamp timeObj = new Timestamp(entryEndTimeStamp.getTime());
										objList.add(timeObj);
										hmDetails.put(ropObj.getScreening_time(), objList);
										List<Object> objList1 = new ArrayList<Object>();
										objList1.add(entryEndTimeStamp);
										objList1.add("ROP");
										hmDetails1.put(ropObj.getScreening_time(), objList1);
									}
								}
							}

							// USG
							String usgAssessmentQuery = "SELECT obj FROM ScreenUSG as obj where uhid='" + uhid.trim()
									+ "' and screening_time >= '" + entryTimeServerFormat + "' and screening_time <= '"
									+ entryEndTimeServerFormat + "' order by screening_time";
							List<ScreenUSG> usgSet = patientDao.getListFromMappedObjNativeQuery(usgAssessmentQuery);

							if (!BasicUtils.isEmpty(usgSet)) {
								for (ScreenUSG usgObj : usgSet) {
									if (!BasicUtils.isEmpty(usgObj.getIvh_type())
											&& ((usgObj.getIvh_type().equalsIgnoreCase("IVH Grade III"))
											|| (usgObj.getIvh_type().equalsIgnoreCase("IVH Grade IV")))) {
										List<Timestamp> objList = new ArrayList<Timestamp>();
										fromHmTime.add(new Timestamp(usgObj.getScreening_time().getTime()));
										toHmTime.add(new Timestamp(entryEndTimeStamp.getTime()));
										Timestamp timeObj = new Timestamp(entryEndTimeStamp.getTime());
										objList.add(timeObj);
										hmDetails.put(usgObj.getScreening_time(), objList);
										List<Object> objList1 = new ArrayList<Object>();
										objList1.add(entryEndTimeStamp);
										objList1.add("IVHGradeIIIorIV");
										hmDetails1.put(usgObj.getScreening_time(), objList1);
									}
								}
							}

							Timestamp fromInotropesTime = new Timestamp(
									entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							Timestamp toInotropesTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							boolean isInotropesStatus = false;

							String medicationAssessmentQuery = "SELECT obj FROM BabyPrescription as obj where uhid='"
									+ uhid.trim() + "' and medicineorderdate <= '" + entryEndTimeServerFormat
									+ "' and isactive = 'false' and enddate is not null and "
									+ " isedit is null and iscontinuemedication is null order by medicineorderdate asc";
							List<BabyPrescription> prescriptionList = patientDao
									.getListFromMappedObjNativeQuery(medicationAssessmentQuery);
							for (BabyPrescription prescriptionObj : prescriptionList) {
								if (prescriptionObj.getEnddate().getTime() > entryTimeStamp.getTime()) {
									if (prescriptionObj.getMedicineOrderDate().getTime() >= entryTimeStamp.getTime()) {
										if (prescriptionObj.getMedicationtype().equalsIgnoreCase("TYPE0004")) {
											fromInotropesTime = new Timestamp(
													prescriptionObj.getMedicineOrderDate().getTime());
											isInotropesStatus = true;
										}
									} else {
										if (prescriptionObj.getMedicationtype().equalsIgnoreCase("TYPE0004")) {
											fromInotropesTime = new Timestamp(entryTimeStamp.getTime());
											isInotropesStatus = true;
										}
									}
									if (prescriptionObj.getEnddate().getTime() >= entryEndTimeStamp.getTime()) {
										if (isInotropesStatus
												&& prescriptionObj.getMedicationtype().equalsIgnoreCase("TYPE0004")) {
											toInotropesTime = new Timestamp(entryEndTimeStamp.getTime());
											isInotropesStatus = false;
											fromHmTime.add(new Timestamp(fromInotropesTime.getTime()));
											toHmTime.add(new Timestamp(toInotropesTime.getTime()));
											List<Timestamp> objList = new ArrayList<Timestamp>();
											objList.add(toInotropesTime);
											hmDetails.put(fromInotropesTime, objList);
											List<Object> objList1 = new ArrayList<Object>();
											objList1.add(toInotropesTime);
											objList1.add("Inotropes");
											hmDetails1.put(fromInotropesTime, objList1);
										}
									} else {
										if (isInotropesStatus
												&& prescriptionObj.getMedicationtype().equalsIgnoreCase("TYPE0004")) {
											toInotropesTime = new Timestamp(prescriptionObj.getEnddate().getTime());
											isInotropesStatus = false;
											fromHmTime.add(new Timestamp(fromInotropesTime.getTime()));
											toHmTime.add(new Timestamp(toInotropesTime.getTime()));
											List<Timestamp> objList = new ArrayList<Timestamp>();
											objList.add(toInotropesTime);
											hmDetails.put(fromInotropesTime, objList);
											List<Object> objList1 = new ArrayList<Object>();
											objList1.add(toInotropesTime);
											objList1.add("Inotropes");
											hmDetails1.put(fromInotropesTime, objList1);
										}
									}
								}
							}
							medicationAssessmentQuery = "SELECT obj FROM BabyPrescription as obj where uhid='"
									+ uhid.trim() + "' and medicineorderdate <= '" + entryEndTimeServerFormat
									+ "' and isactive = 'true' and enddate is null order by medicineorderdate asc";
							prescriptionList = patientDao.getListFromMappedObjNativeQuery(medicationAssessmentQuery);
							for (BabyPrescription prescriptionObj : prescriptionList) {
								if (prescriptionObj.getMedicineOrderDate().getTime() >= entryTimeStamp.getTime()) {
									if (prescriptionObj.getMedicationtype().equalsIgnoreCase("TYPE0004")) {
										fromInotropesTime = new Timestamp(
												prescriptionObj.getMedicineOrderDate().getTime());
										isInotropesStatus = true;
									}
								} else {
									if (prescriptionObj.getMedicationtype().equalsIgnoreCase("TYPE0004")) {
										fromInotropesTime = new Timestamp(entryTimeStamp.getTime());
										isInotropesStatus = true;
									}
								}
								if (isInotropesStatus
										&& prescriptionObj.getMedicationtype().equalsIgnoreCase("TYPE0004")) {
									toInotropesTime = new Timestamp(entryEndTimeStamp.getTime());
									isInotropesStatus = false;
									fromHmTime.add(new Timestamp(fromInotropesTime.getTime()));
									toHmTime.add(new Timestamp(toInotropesTime.getTime()));
									List<Timestamp> objList = new ArrayList<Timestamp>();
									objList.add(toInotropesTime);
									hmDetails.put(fromInotropesTime, objList);
									List<Object> objList1 = new ArrayList<Object>();
									objList1.add(toInotropesTime);
									objList1.add("Inotropes");
									hmDetails1.put(fromInotropesTime, objList1);
								}
							}
							// end of medication

							// PPHN and Pneumothorax
							Timestamp fromPPHNTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							Timestamp toPPHNTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							boolean isPPHNStatus = false;

							Timestamp fromPneumoTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							Timestamp toPneumoTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							boolean isPneumoStatus = false;

							int pphnStatus = -1;
							int pneumothoraxStatus = -1;

							List<VwAssesmentRespsystemFinal> rdsSet = new ArrayList<>();
							String rdsAssessmentQuery = "SELECT obj FROM VwAssesmentRespsystemFinal as obj where uhid='"
									+ uhid.trim() + "' and creationtime <= '" + entryTimeServerFormat
									+ "' order by creationtime desc";
							List<VwAssesmentRespsystemFinal> rdsSetTemp = patientDao
									.getListFromMappedObjNativeQuery(rdsAssessmentQuery);
							if (!BasicUtils.isEmpty(rdsSetTemp)) {
								for (VwAssesmentRespsystemFinal assessmentObj : rdsSetTemp) {
									switch (assessmentObj.getEvent()) {

										case "PPHN":
											if (pphnStatus == -1
													&& (assessmentObj.getEventstatus().equalsIgnoreCase("Active"))) {
												pphnStatus = 1;
											} else if (pphnStatus == -1) {
												pphnStatus = 0;
											}
											break;
										case "Pneumothorax":
											if (pneumothoraxStatus == -1
													&& (assessmentObj.getEventstatus().equalsIgnoreCase("Active"))) {
												pneumothoraxStatus = 1;
											} else if (pneumothoraxStatus == -1) {
												pneumothoraxStatus = 0;
											}
											break;
									}
								}
							}

							rdsAssessmentQuery = "SELECT obj FROM VwAssesmentRespsystemFinal as obj where uhid='"
									+ uhid.trim() + "' and creationtime >= '" + entryTimeServerFormat
									+ "' and creationtime <= '" + entryEndTimeServerFormat + "' order by creationtime";
							rdsSetTemp = patientDao.getListFromMappedObjNativeQuery(rdsAssessmentQuery);
							if (!BasicUtils.isEmpty(rdsSetTemp))
								rdsSet.addAll(rdsSetTemp);

							if (!BasicUtils.isEmpty(rdsSet)) {
								for (VwAssesmentRespsystemFinal sarespObj : rdsSet) {
									switch (rdsSet.get(0).getEvent()) {

										case "PPHN":
											if (!(sarespObj.getEventstatus().equalsIgnoreCase("Active"))
													&& pphnStatus == 1) {
												pphnStatus = 0;

												if (isPPHNStatus) {
													toPPHNTime = new Timestamp(sarespObj.getCreationtime().getTime());
													isPPHNStatus = false;
													fromHmTime.add(new Timestamp(fromPPHNTime.getTime()));
													toHmTime.add(new Timestamp(toPPHNTime.getTime()));
													List<Timestamp> objList = new ArrayList<Timestamp>();
													objList.add(toPPHNTime);
													hmDetails.put(fromPPHNTime, objList);
													List<Object> objList1 = new ArrayList<Object>();
													objList1.add(toPPHNTime);
													objList1.add("PPHN");
													hmDetails1.put(fromPPHNTime, objList1);
												}
											} else if ((sarespObj.getEventstatus().equalsIgnoreCase("Active"))
													&& (pphnStatus == 0 || pphnStatus == -1)) {
												pphnStatus = 1;
												fromPPHNTime = new Timestamp(sarespObj.getCreationtime().getTime());
												isPPHNStatus = true;
											}
											break;
										case "Pneumothorax":
											if (!(sarespObj.getEventstatus().equalsIgnoreCase("Active"))
													&& pneumothoraxStatus == 1) {
												pneumothoraxStatus = 0;
												if (isPneumoStatus) {
													toPneumoTime = new Timestamp(sarespObj.getCreationtime().getTime());
													isPneumoStatus = false;
													fromHmTime.add(new Timestamp(fromPneumoTime.getTime()));
													toHmTime.add(new Timestamp(toPneumoTime.getTime()));
													List<Timestamp> objList = new ArrayList<Timestamp>();
													objList.add(toPneumoTime);
													hmDetails.put(fromPneumoTime, objList);
													List<Object> objList1 = new ArrayList<Object>();
													objList1.add(toPPHNTime);
													objList1.add("Pneumothorax");
													hmDetails1.put(fromPPHNTime, objList1);
												}
											} else if ((sarespObj.getEventstatus().equalsIgnoreCase("Active"))
													&& (pneumothoraxStatus == 0 || pneumothoraxStatus == -1)) {
												pneumothoraxStatus = 1;
												fromPneumoTime = new Timestamp(sarespObj.getCreationtime().getTime());
												isPneumoStatus = true;
											}
											break;
									}
								}
							}

							Timestamp startTimeOffset = new Timestamp(entryTimeStamp.getTime());
							Timestamp endTimeOffset = new Timestamp(entryEndTimeStamp.getTime());
							boolean isHM = false;
							String hmReason = "";
							if (!BasicUtils.isEmpty(hmDetails1)) {
								for (Timestamp entry : hmDetails1.keySet()) {
									if (!isHM) {
										isHM = true;
										List<Object> reason = hmDetails1.get(entry);
										hmReason = (String) reason.get(1);
									}
								}
							}
							obj.setHmlmReason(hmReason);

							//
							//							if (fromHmTime.size() > 0 && toHmTime.size() > 0 && fromHmTime.size() == toHmTime.size()) {
							//								while (endTimeOffset.getTime() > startTimeOffset.getTime()) {
							//									boolean isHmLmStatus = false;
							//									for (Timestamp entry : hmDetails.keySet()) {
							//										if(!isHM) {
							//											if(entry.getTime() <= startTimeOffset.getTime()) {
							//												isHM = true;
							//												List<Object> hmlmData = new ArrayList<>();
							//												Timestamp offsetTime = new Timestamp(startTimeOffset.getTime() + offset);
							//												hmlmData.add(offsetTime);
							//												hmlmData.add(1);
							//												hmlmDataFinal.add(hmlmData);
							//												isHmLmStatus = true;
							//												List<Object> reason = hmDetails.get(entry);
							//												toEndTime = (Timestamp) reason.get(0);
							//												break;
							//											}
							//										}else {
							//											if(entry.getTime() >= toEndTime.getTime()) {
							//												List<Object> hmlmData = new ArrayList<>();
							//												Timestamp offsetTime = new Timestamp(startTimeOffset.getTime() + offset);
							//												hmlmData.add(offsetTime);
							//												hmlmData.add(1);
							//												hmlmDataFinal.add(hmlmData);
							//												isHmLmStatus = true;
							//												List<Object> reason = hmDetails.get(entry);
							//												toEndTime = (Timestamp) reason.get(0);
							//												break;
							//											}
							//										}
							//									}if(!isHmLmStatus) {
							//										List<Object> hmlmData = new ArrayList<>();
							//										Timestamp offsetTime = new Timestamp(startTimeOffset.getTime() + offset);
							//										hmlmData.add(offsetTime);
							//										hmlmData.add(0);
							//										hmlmDataFinal.add(hmlmData);
							//									}
							//									startTimeOffset = new Timestamp(startTimeOffset.getTime() + (60 * 1000));
							// 								}
							// 							}


							isHM = false;
							Timestamp toEndTime = new Timestamp(new Date().getTime());
							List<Long> timeList = new ArrayList<Long>();
							if (!BasicUtils.isEmpty(hmDetails)) {
								for (Timestamp entry : hmDetails.keySet()) {
									if (!isHM) {
										isHM = true;
										List<Timestamp> reason = hmDetails.get(entry);
										List<Object> obj1 = new ArrayList<Object>();
										if (entry.getTime() <= entryTimeStamp.getTime()) {
											obj1.add(entryTimeStamp.getTime());
											timeList.add(entryTimeStamp.getTime());
										} else {
											List<Object> obj3 = new ArrayList<Object>();
											obj3.add(entryTimeStamp.getTime());
											timeList.add(entryTimeStamp.getTime());
											obj3.add(0);
											hmlmDataFinal.add(obj3);
											obj1.add(entry.getTime());
											timeList.add(entry.getTime());
										}
										obj1.add(1);
										List<Object> obj2 = new ArrayList<Object>();
										obj2.add(reason.get(0).getTime());
										timeList.add(reason.get(0).getTime());
										obj2.add(0);
										hmlmDataFinal.add(obj1);
										hmlmDataFinal.add(obj2);
										toEndTime = (Timestamp) reason.get(0);
									} else {
										if (entry.getTime() >= toEndTime.getTime()) {
											List<Timestamp> reason = hmDetails.get(entry);
											List<Object> obj1 = new ArrayList<Object>();
											obj1.add(entry.getTime());
											timeList.add(entry.getTime());
											obj1.add(1);
											List<Object> obj2 = new ArrayList<Object>();
											obj2.add(reason.get(0).getTime());
											timeList.add(reason.get(0).getTime());
											obj2.add(0);
											hmlmDataFinal.add(obj1);
											hmlmDataFinal.add(obj2);
											toEndTime = (Timestamp) reason.get(0);
										}
									}
								}
							}
							//							int index = 0;
							//							int currentValue = 0;
							//							while (endTimeOffset.getTime() > startTimeOffset.getTime()) {
							//
							//								if(!BasicUtils.isEmpty(hmlmDataFinal) && index < hmlmDataFinal.size()) {
							//									Long currentObj = timeList.get(index);
							//									Timestamp currentTime = new Timestamp(currentObj);
							//									if(currentTime.getTime() <= startTimeOffset.getTime()) {
							//										currentValue = (Integer) hmlmDataFinal.get(index).get(1);
							//										index++;
							//									}
							//								}
							//								List<Object> hmlmData = new ArrayList<>();
							//								Timestamp offsetTime = new Timestamp(startTimeOffset.getTime() + offset);
							//								hmlmData.add(offsetTime);
							//								hmlmData.add(currentValue);
							//								hmlmDataFinalSet.add(hmlmData);
							//								startTimeOffset = new Timestamp(startTimeOffset.getTime() + (60 * 1000));
							//
							//							}
						}

						List<DeviceGraphAssessmentJSON> jaundiceJson = new ArrayList<DeviceGraphAssessmentJSON>();
						List<DeviceGraphAssessmentJSON> renalFailureJson = new ArrayList<DeviceGraphAssessmentJSON>();
						List<DeviceGraphAssessmentJSON> hypoglycemiaJson = new ArrayList<DeviceGraphAssessmentJSON>();
						List<DeviceGraphAssessmentJSON> sepsisJson = new ArrayList<DeviceGraphAssessmentJSON>();
						List<DeviceGraphAssessmentJSON> rdsJson = new ArrayList<DeviceGraphAssessmentJSON>();
						DeviceGraphAssessmentJSON rdsObj = new DeviceGraphAssessmentJSON();
						List<DeviceGraphAssessmentJSON> apneaJson = new ArrayList<DeviceGraphAssessmentJSON>();
						DeviceGraphAssessmentJSON apneaObj = new DeviceGraphAssessmentJSON();
						List<DeviceGraphAssessmentJSON> pneumothoraxJson = new ArrayList<DeviceGraphAssessmentJSON>();
						DeviceGraphAssessmentJSON pneumothoraxObj = new DeviceGraphAssessmentJSON();
						List<DeviceGraphAssessmentJSON> pphnJson = new ArrayList<DeviceGraphAssessmentJSON>();
						DeviceGraphAssessmentJSON pphnObj = new DeviceGraphAssessmentJSON();
						List<DeviceGraphAssessmentJSON> asphyxiaJson = new ArrayList<DeviceGraphAssessmentJSON>();
						DeviceGraphAssessmentJSON asphyxiaObj = new DeviceGraphAssessmentJSON();
						List<DeviceGraphAssessmentJSON> seizuresJson = new ArrayList<DeviceGraphAssessmentJSON>();
						DeviceGraphAssessmentJSON seizuresObj = new DeviceGraphAssessmentJSON();
						List<DeviceGraphAssessmentJSON> feedIntoleranceJson = new ArrayList<DeviceGraphAssessmentJSON>();
						List<DeviceGraphAssessmentJSON> necJson = new ArrayList<DeviceGraphAssessmentJSON>();

						if (graphData.isAssessment()) {
							obj.setAssessment(graphData.isAssessment());
							// beginning of assessments
							// Jaundice
							DeviceGraphAssessmentJSON jaundObj = new DeviceGraphAssessmentJSON();
							int jaundiceStatus = -1;
							List<SaJaundice> jaundiceSet = new ArrayList<>();
							String jaundiceAssessmentQuery = "SELECT obj FROM SaJaundice as obj where uhid='"
									+ uhid.trim() + "' and assessment_time <= '" + entryTimeServerFormat
									+ "' order by assessment_time desc";
							List<SaJaundice> jaundiceSetTemp = patientDao
									.getListFromMappedObjNativeQuery(jaundiceAssessmentQuery);
							if (!BasicUtils.isEmpty(jaundiceSetTemp)) {
								if (jaundiceSetTemp.get(0).getJaundicestatus().equalsIgnoreCase("Yes")) {
									jaundiceStatus = 1;
									jaundObj.setStartDate(new Timestamp(entryTimeStamp.getTime()));
								} else {
									jaundiceStatus = 0;
								}
							}

							jaundiceAssessmentQuery = "SELECT obj FROM SaJaundice as obj where uhid='" + uhid.trim()
									+ "' and assessment_time >= '" + entryTimeServerFormat
									+ "' and assessment_time <= '" + entryEndTimeServerFormat
									+ "' order by assessment_time";
							jaundiceSetTemp = patientDao.getListFromMappedObjNativeQuery(jaundiceAssessmentQuery);
							if (!BasicUtils.isEmpty(jaundiceSetTemp))
								jaundiceSet.addAll(jaundiceSetTemp);
							if (!BasicUtils.isEmpty(jaundiceSet)) {
								for (SaJaundice sajaundObj : jaundiceSet) {
									if (!(sajaundObj.getJaundicestatus().equalsIgnoreCase("Yes"))
											&& jaundiceStatus == 1) {
										jaundiceStatus = 0;
										jaundObj.setEndDate(sajaundObj.getAssessmentTime());
										jaundiceJson.add(jaundObj);
										jaundObj = new DeviceGraphAssessmentJSON();
									} else if ((sajaundObj.getJaundicestatus().equalsIgnoreCase("Yes"))
											&& (jaundiceStatus == 0 || jaundiceStatus == -1)) {
										jaundObj.setStartDate(sajaundObj.getAssessmentTime());
										jaundiceStatus = 1;
									}
								}
							}
							if (jaundiceStatus == 1) {
								jaundObj.setEndDate(new Timestamp(entryEndTimeStamp.getTime()));
								jaundiceJson.add(jaundObj);
							}

							// Renal
							DeviceGraphAssessmentJSON renalObj = new DeviceGraphAssessmentJSON();
							int renalStatus = -1;
							List<SaRenalfailure> renalSet = new ArrayList<>();
							String renalAssessmentQuery = "SELECT obj FROM SaRenalfailure as obj where uhid='"
									+ uhid.trim() + "' and assessment_time <= '" + entryTimeServerFormat
									+ "' order by assessment_time desc";
							List<SaRenalfailure> renalSetTemp = patientDao
									.getListFromMappedObjNativeQuery(renalAssessmentQuery);
							if (!BasicUtils.isEmpty(renalSetTemp)) {
								if (renalSetTemp.get(0).getRenalstatus().equalsIgnoreCase("Yes")) {
									renalStatus = 1;
									renalObj.setStartDate(new Timestamp(entryTimeStamp.getTime()));
								} else {
									renalStatus = 0;
								}
							}

							renalAssessmentQuery = "SELECT obj FROM SaRenalfailure as obj where uhid='" + uhid.trim()
									+ "' and assessment_time >= '" + entryTimeServerFormat
									+ "' and assessment_time <= '" + entryEndTimeServerFormat
									+ "' order by assessment_time";
							renalSetTemp = patientDao.getListFromMappedObjNativeQuery(renalAssessmentQuery);
							if (!BasicUtils.isEmpty(renalSetTemp))
								renalSet.addAll(renalSetTemp);
							if (!BasicUtils.isEmpty(renalSet)) {
								for (SaRenalfailure saRenalObj : renalSet) {
									if (!(saRenalObj.getRenalstatus().equalsIgnoreCase("Yes")) && renalStatus == 1) {
										renalStatus = 0;
										renalObj.setEndDate(saRenalObj.getAssessmentTime());
										renalFailureJson.add(renalObj);
										renalObj = new DeviceGraphAssessmentJSON();
									} else if ((saRenalObj.getRenalstatus().equalsIgnoreCase("Yes"))
											&& (renalStatus == 0 || renalStatus == -1)) {
										renalObj.setStartDate(saRenalObj.getAssessmentTime());
										renalStatus = 1;
									}
								}
							}
							if (renalStatus == 1) {
								renalObj.setEndDate(new Timestamp(entryEndTimeStamp.getTime()));
								renalFailureJson.add(renalObj);
							}

							// Hypoglycemia
							DeviceGraphAssessmentJSON hypoglycemiaObj = new DeviceGraphAssessmentJSON();
							int hypoglycemiaStatus = -1;
							List<SaHypoglycemia> hypoglycemiaSet = new ArrayList<>();
							String hypoglycemiaAssessmentQuery = "SELECT obj FROM SaHypoglycemia as obj where uhid='"
									+ uhid.trim() + "' and assessment_time <= '" + entryTimeServerFormat
									+ "' order by assessment_time desc";
							List<SaHypoglycemia> hypoglycemiaSetTemp = patientDao
									.getListFromMappedObjNativeQuery(hypoglycemiaAssessmentQuery);
							if (!BasicUtils.isEmpty(hypoglycemiaSetTemp)) {
								if (hypoglycemiaSetTemp.get(0).getHypoglycemiaEventStatus().equalsIgnoreCase("Yes")) {
									hypoglycemiaStatus = 1;
									hypoglycemiaObj.setStartDate(new Timestamp(entryTimeStamp.getTime()));
								} else {
									hypoglycemiaStatus = 0;
								}
							}

							hypoglycemiaAssessmentQuery = "SELECT obj FROM SaHypoglycemia as obj where uhid='"
									+ uhid.trim() + "' and assessment_time >= '" + entryTimeServerFormat
									+ "' and assessment_time <= '" + entryEndTimeServerFormat
									+ "' order by assessment_time";
							hypoglycemiaSetTemp = patientDao
									.getListFromMappedObjNativeQuery(hypoglycemiaAssessmentQuery);
							if (!BasicUtils.isEmpty(hypoglycemiaSetTemp))
								hypoglycemiaSet.addAll(hypoglycemiaSetTemp);
							if (!BasicUtils.isEmpty(hypoglycemiaSet)) {
								for (SaHypoglycemia saHypoglycemiaObj : hypoglycemiaSet) {
									if (!(saHypoglycemiaObj.getHypoglycemiaEventStatus().equalsIgnoreCase("Yes"))
											&& hypoglycemiaStatus == 1) {
										hypoglycemiaStatus = 0;
										hypoglycemiaObj.setEndDate(saHypoglycemiaObj.getAssessmentTime());
										hypoglycemiaJson.add(hypoglycemiaObj);
										hypoglycemiaObj = new DeviceGraphAssessmentJSON();
									} else if ((saHypoglycemiaObj.getHypoglycemiaEventStatus().equalsIgnoreCase("Yes"))
											&& (hypoglycemiaStatus == 0 || hypoglycemiaStatus == -1)) {
										hypoglycemiaObj.setStartDate(saHypoglycemiaObj.getAssessmentTime());
										hypoglycemiaStatus = 1;
									}
								}
							}
							if (hypoglycemiaStatus == 1) {
								hypoglycemiaObj.setEndDate(new Timestamp(entryEndTimeStamp.getTime()));
								hypoglycemiaJson.add(hypoglycemiaObj);
							}

							// Sepsis
							DeviceGraphAssessmentJSON sepsisObj = new DeviceGraphAssessmentJSON();
							int sepsisStatus = -1;
							List<SaSepsis> sepsisSet = new ArrayList<>();
							String sepsisAssessmentQuery = "SELECT obj FROM SaSepsis as obj where uhid='" + uhid.trim()
									+ "' and assessment_time <= '" + entryTimeServerFormat
									+ "' order by assessment_time desc";
							List<SaSepsis> sepsisSetTemp = patientDao
									.getListFromMappedObjNativeQuery(sepsisAssessmentQuery);
							if (!BasicUtils.isEmpty(sepsisSetTemp)) {
								if (sepsisSetTemp.get(0).getEventstatus().equalsIgnoreCase("yes")) {
									sepsisStatus = 1;
									sepsisObj.setStartDate(new Timestamp(entryTimeStamp.getTime()));
								} else {
									sepsisStatus = 0;
								}
							}

							sepsisAssessmentQuery = "SELECT obj FROM SaSepsis as obj where uhid='" + uhid.trim()
									+ "' and assessment_time >= '" + entryTimeServerFormat
									+ "' and assessment_time <= '" + entryEndTimeServerFormat
									+ "' order by assessment_time";
							sepsisSetTemp = patientDao.getListFromMappedObjNativeQuery(sepsisAssessmentQuery);
							if (!BasicUtils.isEmpty(sepsisSetTemp))
								sepsisSet.addAll(sepsisSetTemp);

							if (!BasicUtils.isEmpty(sepsisSet)) {
								for (SaSepsis sasepsisObj : sepsisSet) {
									if (!(sasepsisObj.getEventstatus().equalsIgnoreCase("yes")) && sepsisStatus == 1) {
										sepsisStatus = 0;
										sepsisObj.setEndDate(sasepsisObj.getAssessmentTime());
										sepsisJson.add(sepsisObj);
										sepsisObj = new DeviceGraphAssessmentJSON();
									} else if ((sasepsisObj.getEventstatus().equalsIgnoreCase("yes"))
											&& (sepsisStatus == 0 || sepsisStatus == -1)) {
										sepsisObj.setStartDate(sasepsisObj.getAssessmentTime());
										sepsisStatus = 1;
									}

								}
							}
							if (sepsisStatus == 1) {
								sepsisObj.setEndDate(new Timestamp(entryEndTimeStamp.getTime()));
								sepsisJson.add(sepsisObj);
							}

							// NEC
							DeviceGraphAssessmentJSON necObj = new DeviceGraphAssessmentJSON();
							int necStatus = -1;
							List<SaNec> necSet = new ArrayList<>();
							String necAssessmentQuery = "SELECT obj FROM SaNec as obj where uhid='" + uhid.trim()
									+ "' and assessment_time <= '" + entryTimeServerFormat
									+ "' order by assessment_time desc";
							List<SaNec> necSetTemp = patientDao
									.getListFromMappedObjNativeQuery(necAssessmentQuery);
							if (!BasicUtils.isEmpty(necSetTemp)) {
								if (necSetTemp.get(0).getEventstatus().equalsIgnoreCase("yes")) {
									necStatus = 1;
									necObj.setStartDate(new Timestamp(entryTimeStamp.getTime()));
								} else {
									necStatus = 0;
								}
							}

							necAssessmentQuery = "SELECT obj FROM SaNec as obj where uhid='" + uhid.trim()
									+ "' and assessment_time >= '" + entryTimeServerFormat
									+ "' and assessment_time <= '" + entryEndTimeServerFormat
									+ "' order by assessment_time";
							necSetTemp = patientDao.getListFromMappedObjNativeQuery(necAssessmentQuery);
							if (!BasicUtils.isEmpty(necSetTemp))
								necSet.addAll(necSetTemp);

							if (!BasicUtils.isEmpty(necSet)) {
								for (SaNec sanecObj : necSet) {
									if (!(sanecObj.getEventstatus().equalsIgnoreCase("yes")) && sepsisStatus == 1) {
										sepsisStatus = 0;
										necObj.setEndDate(sanecObj.getAssessmentTime());
										necJson.add(necObj);
										necObj = new DeviceGraphAssessmentJSON();
									} else if ((sanecObj.getEventstatus().equalsIgnoreCase("yes"))
											&& (necStatus == 0 || necStatus == -1)) {
										necObj.setStartDate(sanecObj.getAssessmentTime());
										necStatus = 1;
									}

								}
							}
							if (necStatus == 1) {
								necObj.setEndDate(new Timestamp(entryEndTimeStamp.getTime()));
								necJson.add(necObj);
							}

							// RDS
							int rdsStatus = -1;
							int pphnStatus = -1;
							int apneaStatus = -1;
							int pneumothoraxStatus = -1;

							List<VwAssesmentRespsystemFinal> rdsSet = new ArrayList<>();
							String rdsAssessmentQuery = "SELECT obj FROM VwAssesmentRespsystemFinal as obj where uhid='"
									+ uhid.trim() + "' and creationtime <= '" + entryTimeServerFormat
									+ "' order by creationtime desc";
							List<VwAssesmentRespsystemFinal> rdsSetTemp = patientDao
									.getListFromMappedObjNativeQuery(rdsAssessmentQuery);
							if (!BasicUtils.isEmpty(rdsSetTemp)) {
								for (VwAssesmentRespsystemFinal assessmentObj : rdsSetTemp) {
									switch (assessmentObj.getEvent()) {
										case "Respiratory Distress":
											if (rdsStatus == -1
													&& (assessmentObj.getEventstatus().equalsIgnoreCase("Active"))) {
												rdsStatus = 1;
												rdsObj.setStartDate(new Timestamp(entryTimeStamp.getTime()));
											} else if (rdsStatus == -1) {
												rdsStatus = 0;
											}
											break;
										case "Apnea":
											if (apneaStatus == -1
													&& (assessmentObj.getEventstatus().equalsIgnoreCase("Active"))) {
												apneaStatus = 1;
												apneaObj.setStartDate(new Timestamp(entryTimeStamp.getTime()));
											} else if (apneaStatus == -1) {
												apneaStatus = 0;
											}
											break;
										case "PPHN":
											if (pphnStatus == -1
													&& (assessmentObj.getEventstatus().equalsIgnoreCase("Active"))) {
												pphnStatus = 1;
												pphnObj.setStartDate(new Timestamp(entryTimeStamp.getTime()));
											} else if (pphnStatus == -1) {
												pphnStatus = 0;
											}
											break;
										case "Pneumothorax":
											if (pneumothoraxStatus == -1
													&& (assessmentObj.getEventstatus().equalsIgnoreCase("Active"))) {
												pneumothoraxStatus = 1;
												pneumothoraxObj.setStartDate(new Timestamp(entryTimeStamp.getTime()));
											} else if (pneumothoraxStatus == -1) {
												pneumothoraxStatus = 0;
											}
											break;
									}
								}
							}

							rdsAssessmentQuery = "SELECT obj FROM VwAssesmentRespsystemFinal as obj where uhid='"
									+ uhid.trim() + "' and creationtime >= '" + entryTimeServerFormat
									+ "' and creationtime <= '" + entryEndTimeServerFormat + "' order by creationtime";
							rdsSetTemp = patientDao.getListFromMappedObjNativeQuery(rdsAssessmentQuery);
							if (!BasicUtils.isEmpty(rdsSetTemp))
								rdsSet.addAll(rdsSetTemp);

							if (!BasicUtils.isEmpty(rdsSet)) {
								for (VwAssesmentRespsystemFinal sarespObj : rdsSet) {
									switch (rdsSet.get(0).getEvent()) {
										case "Respiratory Distress":

											if (!(sarespObj.getEventstatus().equalsIgnoreCase("Active"))
													&& rdsStatus == 1) {
												rdsStatus = 0;
												rdsObj.setEndDate(sarespObj.getCreationtime());
												rdsJson.add(rdsObj);
												rdsObj = new DeviceGraphAssessmentJSON();
											} else if ((sarespObj.getEventstatus().equalsIgnoreCase("Active"))
													&& (rdsStatus == 0 || rdsStatus == -1)) {
												rdsObj.setStartDate(sarespObj.getCreationtime());
												rdsStatus = 1;
											}

											break;
										case "Apnea":
											if (!(sarespObj.getEventstatus().equalsIgnoreCase("Active"))
													&& apneaStatus == 1) {
												apneaStatus = 0;
												apneaObj.setEndDate(sarespObj.getCreationtime());
												apneaJson.add(apneaObj);
												apneaObj = new DeviceGraphAssessmentJSON();
											} else if ((sarespObj.getEventstatus().equalsIgnoreCase("Active"))
													&& (apneaStatus == 0 || apneaStatus == -1)) {
												apneaObj.setStartDate(sarespObj.getCreationtime());
												apneaStatus = 1;
											}
											break;
										case "PPHN":
											if (!(sarespObj.getEventstatus().equalsIgnoreCase("Active"))
													&& pphnStatus == 1) {
												pphnStatus = 0;
												pphnObj.setEndDate(sarespObj.getCreationtime());
												pphnJson.add(pphnObj);
												pphnObj = new DeviceGraphAssessmentJSON();
											} else if ((sarespObj.getEventstatus().equalsIgnoreCase("Active"))
													&& (pphnStatus == 0 || pphnStatus == -1)) {
												pphnObj.setStartDate(sarespObj.getCreationtime());
												pphnStatus = 1;
											}
											break;
										case "Pneumothorax":
											if (!(sarespObj.getEventstatus().equalsIgnoreCase("Active"))
													&& pneumothoraxStatus == 1) {
												pneumothoraxStatus = 0;
												pneumothoraxObj.setEndDate(sarespObj.getCreationtime());
												pneumothoraxJson.add(pneumothoraxObj);
												pneumothoraxObj = new DeviceGraphAssessmentJSON();
											} else if ((sarespObj.getEventstatus().equalsIgnoreCase("Active"))
													&& (pneumothoraxStatus == 0 || pneumothoraxStatus == -1)) {
												pneumothoraxObj.setStartDate(sarespObj.getCreationtime());
												pneumothoraxStatus = 1;
											}
											break;
									}
								}
							}
							if (rdsStatus == 1) {
								rdsObj.setEndDate(new Timestamp(entryEndTimeStamp.getTime()));
								rdsJson.add(rdsObj);
							}
							if (pphnStatus == 1) {
								pphnObj.setEndDate(new Timestamp(entryEndTimeStamp.getTime()));
								pphnJson.add(pphnObj);
							}
							if (apneaStatus == 1) {
								apneaObj.setEndDate(new Timestamp(entryEndTimeStamp.getTime()));
								apneaJson.add(apneaObj);
							}
							if (pneumothoraxStatus == 1) {
								pneumothoraxObj.setEndDate(new Timestamp(entryEndTimeStamp.getTime()));
								pneumothoraxJson.add(pneumothoraxObj);
							}

							// CNS
							int asphyxiaStatus = -1;
							int seizuresStatus = -1;

							List<VwAssesmentCnsFinal> cnsSet = new ArrayList<>();
							String cnsAssessmentQuery = "SELECT obj FROM VwAssesmentCnsFinal as obj where uhid='"
									+ uhid.trim() + "' and creationtime <= '" + entryTimeServerFormat
									+ "' order by creationtime desc";
							List<VwAssesmentCnsFinal> cnsSetTemp = patientDao
									.getListFromMappedObjNativeQuery(cnsAssessmentQuery);
							if (!BasicUtils.isEmpty(cnsSetTemp)) {
								for (VwAssesmentCnsFinal assessmentObj : cnsSetTemp) {
									switch (assessmentObj.getEvent()) {
										case "Asphyxia":
											if (asphyxiaStatus == -1
													&& (assessmentObj.getEventstatus().equalsIgnoreCase("yes"))) {
												asphyxiaObj.setStartDate(new Timestamp(entryTimeStamp.getTime()));
												asphyxiaStatus = 1;
											} else if (asphyxiaStatus == -1) {
												asphyxiaStatus = 0;
											}
											break;
										case "Seizures":
											if (seizuresStatus == -1
													&& (assessmentObj.getEventstatus().equalsIgnoreCase("yes"))) {
												seizuresObj.setStartDate(new Timestamp(entryTimeStamp.getTime()));
												seizuresStatus = 1;
											} else if (seizuresStatus == -1) {
												seizuresStatus = 0;
											}
											break;
									}
								}
							}

							cnsAssessmentQuery = "SELECT obj FROM VwAssesmentCnsFinal as obj where uhid='" + uhid.trim()
									+ "' and creationtime >= '" + entryTimeServerFormat + "' and creationtime <= '"
									+ entryEndTimeServerFormat + "' order by creationtime";
							cnsSetTemp = patientDao.getListFromMappedObjNativeQuery(cnsAssessmentQuery);
							if (!BasicUtils.isEmpty(cnsSetTemp))
								cnsSet.addAll(cnsSetTemp);

							if (!BasicUtils.isEmpty(cnsSet)) {
								for (VwAssesmentCnsFinal sacnsObj : cnsSet) {
									switch (cnsSet.get(0).getEvent()) {
										case "Asphyxia":
											if (!(sacnsObj.getEventstatus().equalsIgnoreCase("yes"))
													&& asphyxiaStatus == 1) {
												asphyxiaStatus = 0;
												asphyxiaObj.setEndDate(sacnsObj.getCreationtime());
												asphyxiaJson.add(asphyxiaObj);
												asphyxiaObj = new DeviceGraphAssessmentJSON();
											} else if ((sacnsObj.getEventstatus().equalsIgnoreCase("yes"))
													&& (asphyxiaStatus == 0 || asphyxiaStatus == -1)) {
												asphyxiaObj.setStartDate(sacnsObj.getCreationtime());
												asphyxiaStatus = 1;
											}

											break;
										case "Seizures":
											if (!(sacnsObj.getEventstatus().equalsIgnoreCase("yes"))
													&& seizuresStatus == 1) {
												seizuresStatus = 0;
												seizuresObj.setEndDate(sacnsObj.getCreationtime());
												seizuresJson.add(seizuresObj);
												seizuresObj = new DeviceGraphAssessmentJSON();
											} else if ((sacnsObj.getEventstatus().equalsIgnoreCase("yes"))
													&& seizuresStatus == 0 || (seizuresStatus == -1)) {
												seizuresObj.setStartDate(sacnsObj.getCreationtime());
												seizuresStatus = 1;
											}
											break;
									}
								}
							}
							if (asphyxiaStatus == 1) {
								asphyxiaObj.setEndDate(new Timestamp(entryEndTimeStamp.getTime()));
								asphyxiaJson.add(asphyxiaObj);
							}
							if (seizuresStatus == 1) {
								seizuresObj.setEndDate(new Timestamp(entryEndTimeStamp.getTime()));
								seizuresJson.add(seizuresObj);
							}

							// Feed Intolerance
							DeviceGraphAssessmentJSON feedIntoleranceObj = new DeviceGraphAssessmentJSON();
							int feedIntoleranceStatus = -1;
							List<SaFeedIntolerance> feedIntoleranceSet = new ArrayList<>();
							String feedIntoleranceAssessmentQuery = "SELECT obj FROM SaFeedIntolerance as obj where uhid='"
									+ uhid.trim() + "' and assessment_time <= '" + entryTimeServerFormat
									+ "' order by assessment_time desc";
							List<SaFeedIntolerance> feedIntoleranceSetTemp = patientDao
									.getListFromMappedObjNativeQuery(feedIntoleranceAssessmentQuery);
							if (!BasicUtils.isEmpty(feedIntoleranceSetTemp)) {
								if (feedIntoleranceSetTemp.get(0).getFeedIntoleranceStatus().equalsIgnoreCase("Yes")) {
									feedIntoleranceStatus = 1;
									feedIntoleranceObj.setStartDate(new Timestamp(entryTimeStamp.getTime()));
								} else {
									feedIntoleranceStatus = 0;
								}
							}

							feedIntoleranceAssessmentQuery = "SELECT obj FROM SaFeedIntolerance as obj where uhid='"
									+ uhid.trim() + "' and assessment_time >= '" + entryTimeServerFormat
									+ "' and assessment_time <= '" + entryEndTimeServerFormat
									+ "' order by assessment_time";
							feedIntoleranceSetTemp = patientDao
									.getListFromMappedObjNativeQuery(feedIntoleranceAssessmentQuery);
							if (!BasicUtils.isEmpty(feedIntoleranceSetTemp))
								feedIntoleranceSet.addAll(feedIntoleranceSetTemp);
							if (!BasicUtils.isEmpty(feedIntoleranceSet)) {
								for (SaFeedIntolerance safeedIntoleranceObj : feedIntoleranceSet) {
									if (!(safeedIntoleranceObj.getFeedIntoleranceStatus().equalsIgnoreCase("Yes"))
											&& feedIntoleranceStatus == 1) {
										feedIntoleranceStatus = 0;
										feedIntoleranceObj.setEndDate(safeedIntoleranceObj.getAssessmentTime());
										feedIntoleranceJson.add(feedIntoleranceObj);
										feedIntoleranceObj = new DeviceGraphAssessmentJSON();
									} else if ((safeedIntoleranceObj.getFeedIntoleranceStatus().equalsIgnoreCase("Yes"))
											&& (feedIntoleranceStatus == 0 || feedIntoleranceStatus == -1)) {
										feedIntoleranceObj.setStartDate(safeedIntoleranceObj.getAssessmentTime());
										feedIntoleranceStatus = 1;
									}
								}
							}
							if (feedIntoleranceStatus == 1) {
								feedIntoleranceObj.setEndDate(new Timestamp(entryEndTimeStamp.getTime()));
								feedIntoleranceJson.add(feedIntoleranceObj);
							}
							// end of assessment
						}

						List<DeviceGraphMedicationJSON> medicineList = new ArrayList<DeviceGraphMedicationJSON>();
						if (graphData.isMedication()) {
							obj.setMedication(graphData.isMedication());

							String medicationAssessmentQuery = "SELECT obj FROM BabyPrescription as obj where uhid='"
									+ uhid.trim() + "' and medicineorderdate <= '" + entryEndTimeServerFormat
									+ "' and isactive = 'false' and enddate is not null and "
									+ " isedit is null and iscontinuemedication is null order by medicineorderdate asc";
							List<BabyPrescription> prescriptionList = patientDao
									.getListFromMappedObjNativeQuery(medicationAssessmentQuery);
							for (BabyPrescription prescriptionObj : prescriptionList) {
								if (prescriptionObj.getEnddate().getTime() > entryTimeStamp.getTime()) {
									DeviceGraphMedicationJSON medicine = new DeviceGraphMedicationJSON();
									medicine.setMedicinename(prescriptionObj.getMedicinename());
									if (prescriptionObj.getMedicineOrderDate().getTime() >= entryTimeStamp.getTime()) {
										medicine.setStartDate(prescriptionObj.getMedicineOrderDate());
									} else {
										medicine.setStartDate(new Timestamp(entryTimeStamp.getTime()));
									}
									if (prescriptionObj.getEnddate().getTime() >= entryEndTimeStamp.getTime()) {
										medicine.setEndDate(new Timestamp(entryEndTimeStamp.getTime()));
									} else {
										medicine.setEndDate(prescriptionObj.getEnddate());
									}
									medicineList.add(medicine);
								}
							}
							medicationAssessmentQuery = "SELECT obj FROM BabyPrescription as obj where uhid='"
									+ uhid.trim() + "' and medicineorderdate <= '" + entryEndTimeServerFormat
									+ "' and isactive = 'true' and enddate is null order by medicineorderdate asc";
							prescriptionList = patientDao.getListFromMappedObjNativeQuery(medicationAssessmentQuery);
							for (BabyPrescription prescriptionObj : prescriptionList) {
								DeviceGraphMedicationJSON medicine = new DeviceGraphMedicationJSON();
								medicine.setMedicinename(prescriptionObj.getMedicinename());
								if (prescriptionObj.getMedicineOrderDate().getTime() >= entryTimeStamp.getTime()) {
									medicine.setStartDate(prescriptionObj.getMedicineOrderDate());
								} else {
									medicine.setStartDate(new Timestamp(entryTimeStamp.getTime()));
								}
								medicine.setEndDate(new Timestamp(entryEndTimeStamp.getTime()));
								medicineList.add(medicine);
							}
							// end of medication
						}

						if (graphData.isBloodGas()) {
							obj.setBloodGas(graphData.isBloodGas());

							// beginning of bloodgas
							System.out.println("offset = " + offset);

							Date entryTimeStampWithOffset = new Date(entryTimeStamp.getTime() + offset);
							Date entryEndTimeStampWithOffset = new Date(entryEndTimeStamp.getTime() + offset);
							String entryTimeServerFormatWithOffset = formatter.format(entryTimeStampWithOffset);
							String entryEndTimeServerFormatWithOffset = formatter.format(entryEndTimeStampWithOffset);

							String bloodgasQuery = "SELECT obj FROM NursingBloodGas as obj where uhid='" + uhid.trim()
									+ "' and entrydate <= '" + entryEndTimeServerFormatWithOffset + "' and entrydate >= '"
									+ entryTimeServerFormatWithOffset + "' order by entrydate";
							List<NursingBloodGas> bloodgasQueryList = patientDao
									.getListFromMappedObjNativeQuery(bloodgasQuery);
							if (!BasicUtils.isEmpty(bloodgasQueryList))
								obj.setBloodGasList(bloodgasQueryList);
							// end of bloodgas
						}
						List<DeviceGraphMedicationJSON> proceduresMasterList = new ArrayList<DeviceGraphMedicationJSON>();

						if (graphData.isProcedure()) {
							obj.setProcedure(graphData.isProcedure());

							// beginning of procedures
							String procedureQuery = "SELECT b.procedure_type, b.uhid, b.starttime, b.endtime FROM vw_procedures_usage as b where b.uhid='"
									+ uhid.trim() + "' and b.starttime <= '" + entryEndTimeServerFormat
									+ "' and b.endtime is not null order by b.starttime asc";
							List<Object[]> procedureList = patientDao.getListFromNativeQuery(procedureQuery);
							for (Object[] procedureObj : procedureList) {
								if (!BasicUtils.isEmpty(procedureObj[2]) && !BasicUtils.isEmpty(procedureObj[3])) {
									Timestamp startdate = (Timestamp) procedureObj[2];
									Timestamp enddate = (Timestamp) procedureObj[3];

									if (enddate.getTime() > entryTimeStamp.getTime()) {
										DeviceGraphMedicationJSON procedure = new DeviceGraphMedicationJSON();
										procedure.setMedicinename((String) procedureObj[0]);
										if (startdate.getTime() >= entryTimeStamp.getTime()) {
											procedure.setStartDate(startdate);
										} else {
											procedure.setStartDate(new Timestamp(entryTimeStamp.getTime()));
										}
										if (enddate.getTime() >= entryEndTimeStamp.getTime()) {
											procedure.setEndDate(new Timestamp(entryEndTimeStamp.getTime()));
										} else {
											procedure.setEndDate(enddate);
										}
										proceduresMasterList.add(procedure);
									}
								}
							}
							procedureQuery = "SELECT b.procedure_type, b.uhid, b.starttime, b.endtime FROM vw_procedures_usage as b where b.uhid='"
									+ uhid.trim() + "' and b.starttime <= '" + entryEndTimeServerFormat
									+ "' and b.endtime is null order by b.starttime asc";
							procedureList = patientDao.getListFromNativeQuery(procedureQuery);
							for (Object[] procedureObj : procedureList) {
								if (!BasicUtils.isEmpty(procedureObj[2])) {
									DeviceGraphMedicationJSON procedure = new DeviceGraphMedicationJSON();
									procedure.setMedicinename((String) procedureObj[0]);
									Timestamp startdate = (Timestamp) procedureObj[2];

									if (startdate.getTime() >= entryTimeStamp.getTime()) {
										procedure.setStartDate(startdate);
									} else {
										procedure.setStartDate(new Timestamp(entryTimeStamp.getTime()));
									}
									procedure.setEndDate(new Timestamp(entryEndTimeStamp.getTime()));
									proceduresMasterList.add(procedure);
								}

							}
							// end of procedures
						}

						// lab reports code
						if (graphData.isLabReports()) {
							obj.setLabReports(true);

							HashSet<String> labTestCategoryList = new HashSet<String>();
							String fetchDistinctTest = "SELECT DISTINCT lab_testname,lab_report_date FROM test_result "
									+ "where prn = '" + uhid + "' and lab_report_date >= '" + entryTimeServerFormat
									+ "' and lab_report_date <= '" + entryEndTimeServerFormat + "' ORDER BY lab_report_date,lab_testname ASC";
							List<Object[]> testnameAndDate = inicuDao.getListFromNativeQuery(fetchDistinctTest);

							List<Object[]> itemDetails = null;

							HashMap<String, TreeMap<Timestamp, List<Object[]>>> masterMap = new HashMap<>();
							if (!BasicUtils.isEmpty(testnameAndDate)) {
								for (int i = 0; i < testnameAndDate.size(); i++) {
									Object testname = testnameAndDate.get(i)[0];
									labTestCategoryList.add(testname.toString());

									itemDetails = fetchItemnameAndValue(testname, testnameAndDate.get(i)[1], uhid);

									TreeMap<Timestamp, List<Object[]>> dateItemMap = new TreeMap<>();
									Timestamp testdate = (Timestamp) testnameAndDate.get(i)[1];
									testdate.setSeconds(0);
									if (!BasicUtils.isEmpty(testdate)) {
										dateItemMap.put(testdate, itemDetails);
									}

									// if testname already present
									if (masterMap.containsKey(testnameAndDate.get(i)[0])) {
										TreeMap<Timestamp, List<Object[]>> tempMap = masterMap
												.get(testnameAndDate.get(i)[0]);
										tempMap.put(testdate, itemDetails);
										masterMap.put((String) testnameAndDate.get(i)[0], null);
										masterMap.put((String) testnameAndDate.get(i)[0], tempMap);
									} else {
										if (!BasicUtils.isEmpty(testnameAndDate.get(i)[0].toString()))
											masterMap.put(testnameAndDate.get(i)[0].toString(), dateItemMap);

									}

								}

								obj.setLabTestCategoryList(labTestCategoryList);

							}

							obj.setMasterMap(masterMap);
						}

						// Phototheraphy Duration
						List<DeviceGraphAssessmentJSON> phototheraphyList = new ArrayList<DeviceGraphAssessmentJSON>();

						if (graphData.isPhototheraphy()) {

							obj.setPhototheraphy(graphData.isPhototheraphy());

							// beginning of phototherapy
							String phototheraphyQuery = "SELECT b.uhid, b.assessment_time as starttime,phototherapyvalue FROM sa_jaundice as b where b.uhid='"
									+ uhid.trim() + "' and b.assessment_time >= '" + entryTimeServerFormat
									+ "' and phototherapyvalue is not null order by b.assessment_time asc";

							List<Object[]> phototherapyList = patientDao.getListFromNativeQuery(phototheraphyQuery);
							int startFlag = 0;
							Timestamp startTime = null;
							Timestamp endTime = null;
							for (Object[] phototherapyObj : phototherapyList) {

								DeviceGraphAssessmentJSON phototheraphy = new DeviceGraphAssessmentJSON();

								if (!BasicUtils.isEmpty(phototherapyObj[1]) && !BasicUtils.isEmpty(phototherapyObj[2])) {

									Timestamp startdate = (Timestamp) phototherapyObj[1];
									String phototherapyValue = phototherapyObj[2].toString();

									if (startFlag == 0 && phototherapyValue.equalsIgnoreCase("Start")) {
										startFlag = 1;
										startTime = startdate;
									}

									if (startFlag == 1 && phototherapyValue.equalsIgnoreCase("Stop")) {
										phototheraphy.setStartDate(startTime);
										phototheraphy.setEndDate(startdate);
										phototheraphyList.add(phototheraphy);
										startFlag = 0;
									}
								}
							}
						}

						//  Surfactant usage
						List<Timestamp> surfactantUsage = new ArrayList<>();
						if (graphData.isSurfactantUsage()) {

							obj.setSurfactantUsage(graphData.isSurfactantUsage());
							String surfactantQuery = "SELECT obj FROM SaRespRds as obj WHERE " + "uhid ='"
									+ uhid.trim() + "' " + "AND assessment_time >= '" + entryTimeServerFormat
									+ "' AND assessment_time <= '" + entryEndTimeServerFormat + "' and sufactantname != NULL and surfactant_dose_ml!=NULL order by assessment_time";
							List<SaRespRds> surfactantObjectList = patientDao
									.getListFromMappedObjNativeQuery(surfactantQuery);

							for (SaRespRds respObject : surfactantObjectList) {
								surfactantUsage.add(respObject.getAssessmentTime());
							}
						}

						// Oxygenation Index (INO)
						List<List<Object>> oxygenationIndex = new ArrayList<>();
						if (graphData.isOxygenationIndex()) {

							obj.setOxygenationIndex(graphData.isOxygenationIndex());

							String oxygenationIndexQuery = "SELECT obj FROM SaRespPphn as obj WHERE " + "uhid ='"
									+ uhid.trim() + "' " + "AND assessment_time >= '" + entryTimeServerFormat
									+ "' AND assessment_time <= '" + entryEndTimeServerFormat + "' and oxygenation_index != NULL order by assessment_time";
							List<SaRespPphn> oxygenationObjectList = patientDao
									.getListFromMappedObjNativeQuery(oxygenationIndexQuery);

							for (SaRespPphn pphnObject : oxygenationObjectList) {
								List<Object> tempValue = new ArrayList<>();
								tempValue.add(pphnObject.getAssessmentTime());
								tempValue.add(pphnObject.getOxygenationIndex());
								oxygenationIndex.add(tempValue);
							}
						}

						// Enteral Intake
						List<List<Object>> enteralIntakeList = new ArrayList<>();
						List<List<Object>> parentralIntakeList = new ArrayList<>();
						List<List<Object>> urineList = new ArrayList<>();
						List<List<Object>> adbList = new ArrayList<>();

						if (graphData.isEnteral() || graphData.isParenteral() || graphData.isUrine() || graphData.isAdb()) {
							try {

								obj.setEnteral(graphData.isEnteral());
								obj.setParenteral(graphData.isParenteral());
								obj.setUrine(graphData.isUrine());
								obj.setAdb(graphData.isAdb());

								SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

								Date parsedDate = myDateFormat.parse(entryTimeServerFormat);
								Date toDate = myDateFormat.parse(entryEndTimeServerFormat);

								Timestamp toTimestamp = new java.sql.Timestamp(toDate.getTime());
								Timestamp fromTimestamp = new java.sql.Timestamp(parsedDate.getTime());


								String getNurseOrder = HqlSqlQueryConstants.getNursingExecution(uhid, fromTimestamp, toTimestamp);
								List<NursingIntakeOutput> nursingIntakeOutputsList = patientDao.getListFromMappedObjNativeQuery(getNurseOrder);
								if (nursingIntakeOutputsList != null && !BasicUtils.isEmpty(nursingIntakeOutputsList)) {
									for (NursingIntakeOutput nursingIntakeOutputObject : nursingIntakeOutputsList) {

										DeviceGraphSurfactantJSON enteralIntakeTempList = new DeviceGraphSurfactantJSON();
										DeviceGraphSurfactantJSON parentralIntakeTempList = new DeviceGraphSurfactantJSON();
										DeviceGraphSurfactantJSON urineTempList = new DeviceGraphSurfactantJSON();
										DeviceGraphSurfactantJSON adbTempList = new DeviceGraphSurfactantJSON();

										double totalPreviousEn = 0;
										double totalPreviousPn = 0;

										// Enteral  Intake
										if (nursingIntakeOutputObject.getActualFeed() != null) {
											totalPreviousEn += nursingIntakeOutputObject.getActualFeed();
											totalPreviousEn = Math.round(totalPreviousEn * 100.0) / 100.0;
										}

										// Parenteral  Intake

										if (nursingIntakeOutputObject.getLipid_delivered() != null) {
											totalPreviousPn += (nursingIntakeOutputObject.getLipid_delivered());
										}

										if (nursingIntakeOutputObject.getTpn_delivered() != null) {
											totalPreviousPn += (nursingIntakeOutputObject.getTpn_delivered());
										}

										if (nursingIntakeOutputObject.getReadymadeDeliveredFeed() != null) {
											totalPreviousPn += (nursingIntakeOutputObject.getReadymadeDeliveredFeed());
										}

										if (!BasicUtils.isEmpty(totalPreviousPn)) {
											totalPreviousPn = Math.round(totalPreviousPn * 100.0) / 100.0;
										}

										// Abdominal Girth
										String adbValue = null;
										if (!BasicUtils.isEmpty(nursingIntakeOutputObject.getAbdomenGirth())) {
											adbValue = nursingIntakeOutputObject.getAbdomenGirth();
										}

										// Urine Output
										String urineValue = null;
										if (!BasicUtils.isEmpty(nursingIntakeOutputObject.getUrinePassed()) && nursingIntakeOutputObject.getUrinePassed()) {
											if (!BasicUtils.isEmpty(nursingIntakeOutputObject.getUrine())) {
												urineValue = nursingIntakeOutputObject.getUrine();
											}
										}

										if (totalPreviousEn != 0) {
											List<Object> tempValue = new ArrayList<>();
											tempValue.add(nursingIntakeOutputObject.getEntry_timestamp());
											tempValue.add(totalPreviousEn);
											enteralIntakeList.add(tempValue);
										}

										if (totalPreviousPn != 0) {
											List<Object> tempValue = new ArrayList<>();
											tempValue.add(nursingIntakeOutputObject.getEntry_timestamp());
											tempValue.add(totalPreviousPn);
											parentralIntakeList.add(tempValue);
										}

										if (urineValue != null) {
											List<Object> tempValue = new ArrayList<>();
											tempValue.add(nursingIntakeOutputObject.getEntry_timestamp());
											tempValue.add(urineValue);
											urineList.add(tempValue);
										}

										if (adbValue != null) {
											List<Object> tempValue = new ArrayList<>();
											tempValue.add(nursingIntakeOutputObject.getEntry_timestamp());
											tempValue.add(adbValue);
											adbList.add(tempValue);
										}
									}
								}

							} catch (ParseException e) {
								System.out.println(e);
							}
						}

						// Events
						List<Timestamp> seizuresEvents = new ArrayList<>();
						List<Timestamp> apneaEvents = new ArrayList<>();
						List<Timestamp> disatEvents = new ArrayList<>();

						if (graphData.isEvents()) {
							obj.setEvents(graphData.isEvents());
							Timestamp fromTime = new Timestamp(entryTimeStamp.getTime());
							Timestamp endTime = new Timestamp(entryEndTimeStamp.getTime());
							String nursingEpisodeQuery = HqlSqlQueryConstants.getNursingEpisodeListForChart(uhid, fromTime, endTime);
							List<NursingEpisode> nursingEpisodeList = patientDao.getListFromMappedObjNativeQuery(nursingEpisodeQuery);
							if (!BasicUtils.isEmpty(nursingEpisodeList) && nursingEpisodeList.size() > 0) {

								for (NursingEpisode nursingEpisodeObject : nursingEpisodeList) {

									if (!BasicUtils.isEmpty(nursingEpisodeObject.getSeizures()) && nursingEpisodeObject.getSeizures() == true) {
										if (nursingEpisodeObject.getSeizureDuration() != null && nursingEpisodeObject.getDuration_unit_seizure() != null) {
											seizuresEvents.add(nursingEpisodeObject.getCreationtime());
										}
									}

									// Check for Apnea
									if (!BasicUtils.isEmpty(nursingEpisodeObject.getApnea()) && nursingEpisodeObject.getApnea() == true) {
										if (nursingEpisodeObject.getApneaDuration() != null && nursingEpisodeObject.getDuration_unit_apnea() != null) {
											apneaEvents.add(nursingEpisodeObject.getCreationtime());

										}
									}

									// check for desaturation
									if (!BasicUtils.isEmpty(nursingEpisodeObject.getDesaturation()) && nursingEpisodeObject.getDesaturation() == true) {
										if (nursingEpisodeObject.getDesaturationSpo2() != null) {
											disatEvents.add(nursingEpisodeObject.getCreationtime());
										}
									}
								}
							}
						}

						obj.setHmStatusObject(hmlmDataFinal);
						obj.setMedicineList(medicineList);
						obj.setProcedureList(proceduresMasterList);
						obj.setJaundice(jaundiceJson);
						obj.setRenalFailure(renalFailureJson);
						obj.setHypoglycemia(hypoglycemiaJson);
						obj.setRds(rdsJson);
						obj.setApnea(apneaJson);
						obj.setSepsis(sepsisJson);
						obj.setNec(necJson);
						obj.setPphn(pphnJson);
						obj.setPneumothorax(pneumothoraxJson);
						obj.setSeizures(seizuresJson);
						obj.setAsphyxia(asphyxiaJson);
						obj.setFeedIntolerance(feedIntoleranceJson);

						// Adding the  phototherapy Object
						obj.setPhototherapyList(phototheraphyList);
						obj.setOxygenationIndexList(oxygenationIndex);
						obj.setSurfactantUsageList(surfactantUsage);

						obj.setEnteralIntakeList(enteralIntakeList);
						obj.setParenteralIntakeList(parentralIntakeList);
						obj.setUrineOutputList(urineList);
						obj.setAbdominalGirthList(adbList);

						obj.setSeizuresEvents(seizuresEvents);
						obj.setApneaEvents(apneaEvents);
						obj.setDisaturationEvents(disatEvents);

					}

					// Device Data
					if (!BasicUtils.isEmpty(uhid)) {
						if (!BasicUtils.isEmpty(entryDate) && !BasicUtils.isEmpty(endDate)) {

							if (!graphData.isAssessment() && !graphData.isMedication() && !graphData.isBloodGas() && !graphData.isProcedure()) {
								// get Device Monitor Data
								obj = getDeviceMonitorData(uhid, entryTimeServerFormat, entryEndTimeServerFormat, offset, obj, isDaily);

								// get Ventilator Data
								obj = getVentilatorData(uhid, entryTimeServerFormat, entryEndTimeServerFormat, offset, obj, isDaily);

								// get Incubator Warmer Data
								obj = getIncubatorWarmerData(uhid, entryTimeServerFormat, entryEndTimeServerFormat, offset, obj, isDaily);

								// Get Device Cerebral Oximeter Data
								obj = getDeviceCerebralOximeterData(uhid, entryTimeServerFormat, entryEndTimeServerFormat, offset, obj, isDaily);
							}
						}
					}

					// This has to be done depends one the need i.e if we are using this part on front end
					Set<String> allxAxisDataSet = new TreeSet<>();
					DeviceGraphAxisJson all = new DeviceGraphAxisJson();
					List<String> allxAxis = new ArrayList<>();
					allxAxis.addAll(allxAxisDataSet);
					all.setxAxis(allxAxis);
					obj.setALL(all);
				} catch (Exception e) {
					System.out.println("You are here in the catch block");
					e.printStackTrace();
				}
			}
		}

		return obj;
	}

	public String getPeriTemperaturePriorityBased(DeviceIncubatorWarmerDetail deviceMonitorDetail) {

		// Implement the logic here for choosing value based on the priority
		String myTemperatureValue = null;
		if (!BasicUtils.isEmpty(deviceMonitorDetail.getSkinTemp1())) {
			myTemperatureValue = deviceMonitorDetail.getSkinTemp1();
		} else if (!BasicUtils.isEmpty(deviceMonitorDetail.getSkinTemp2())) {
			myTemperatureValue = deviceMonitorDetail.getSkinTemp2();
		} else if (!BasicUtils.isEmpty(deviceMonitorDetail.getAirTemp())) {
			myTemperatureValue = deviceMonitorDetail.getAirTemp();
		}
		return myTemperatureValue;
	}


	@SuppressWarnings({"unchecked", "deprecation"})
	@Override
	public ActiveFilterGraphDataJson getActiveFiltersData(String uhid, String entryDate, String endDate) {
		ActiveFilterGraphDataJson activeFilterGraphDataObj = new ActiveFilterGraphDataJson();
		try {
			if (!entryDate.equalsIgnoreCase("undefined")) {
				Date entryTimeStamp = CalendarUtility.getDateFormatGMT(CalendarUtility.CLIENT_CRUD_OPERATION).parse(entryDate);

				Date entryEndTimeStamp = CalendarUtility.getDateFormatGMT(CalendarUtility.CLIENT_CRUD_OPERATION).parse(endDate);

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
				String entryTimeServerFormat = formatter.format(entryTimeStamp);
				String entryEndTimeServerFormat = formatter.format(entryEndTimeStamp);

				//Assesments
				List<String> assesmentsList = new ArrayList<>();

				// PPHN
				boolean isPphn = false;
				String pphnAssessmentQuery = "SELECT obj FROM VwAssesmentRespsystemFinal as obj where uhid='"
						+ uhid.trim() + "' and creationtime <= '" + entryTimeServerFormat
						+ "' and event = 'PPHN' order by creationtime desc";
				List<VwAssesmentRespsystemFinal> pphnActiveList = patientDao
						.getListFromMappedObjNativeQuery(pphnAssessmentQuery);
				if (!BasicUtils.isEmpty(pphnActiveList)) {
					if (pphnActiveList.get(0) != null && pphnActiveList.get(0).getEventstatus().equalsIgnoreCase("Active")) {
						isPphn = true;
					}
				}

				if (!isPphn) {
					pphnAssessmentQuery = "SELECT obj FROM VwAssesmentRespsystemFinal as obj where uhid='"
							+ uhid.trim() + "' and creationtime >= '" + entryTimeServerFormat
							+ "' and creationtime <= '" + entryEndTimeServerFormat + "' and event = 'PPHN' "
							+ "and eventstatus = 'Active' order by creationtime";
					pphnActiveList = patientDao.getListFromMappedObjNativeQuery(pphnAssessmentQuery);
					if (!BasicUtils.isEmpty(pphnActiveList)) {
						isPphn = true;
					}
				}

				if (isPphn) {
					assesmentsList.add("PPHN");
				}

				// Pneumothorax
				boolean isPneumothorax = false;
				String pneumothoraxAssessmentQuery = "SELECT obj FROM VwAssesmentRespsystemFinal as obj where uhid='"
						+ uhid.trim() + "' and creationtime <= '" + entryTimeServerFormat
						+ "' and event = 'Pneumothorax' order by creationtime desc";
				List<VwAssesmentRespsystemFinal> pneumothoraxActiveList = patientDao
						.getListFromMappedObjNativeQuery(pneumothoraxAssessmentQuery);
				if (!BasicUtils.isEmpty(pneumothoraxActiveList)) {
					if (pneumothoraxActiveList.get(0) != null && pneumothoraxActiveList.get(0).getEventstatus().equalsIgnoreCase("Active")) {
						isPneumothorax = true;
					}
				}

				if (!isPphn) {
					pneumothoraxAssessmentQuery = "SELECT obj FROM VwAssesmentRespsystemFinal as obj where uhid='"
							+ uhid.trim() + "' and creationtime >= '" + entryTimeServerFormat
							+ "' and creationtime <= '" + entryEndTimeServerFormat + "' and event = 'Pneumothorax' "
							+ "and eventstatus = 'Active' order by creationtime";
					pneumothoraxActiveList = patientDao.getListFromMappedObjNativeQuery(pneumothoraxAssessmentQuery);
					if (!BasicUtils.isEmpty(pneumothoraxActiveList)) {
						isPneumothorax = true;
					}
				}

				if (isPneumothorax) {
					assesmentsList.add("Pneumothorax");
				}

				// Apnea
				boolean isApnea = false;
				String apneaAssessmentQuery = "SELECT obj FROM VwAssesmentRespsystemFinal as obj where uhid='"
						+ uhid.trim() + "' and creationtime <= '" + entryTimeServerFormat
						+ "' and event = 'Apnea' order by creationtime desc";
				List<VwAssesmentRespsystemFinal> apneaActiveList = patientDao
						.getListFromMappedObjNativeQuery(apneaAssessmentQuery);
				if (!BasicUtils.isEmpty(apneaActiveList)) {
					if (apneaActiveList.get(0) != null && apneaActiveList.get(0).getEventstatus().equalsIgnoreCase("Active")) {
						isApnea = true;
					}
				}

				if (!isApnea) {
					apneaAssessmentQuery = "SELECT obj FROM VwAssesmentRespsystemFinal as obj where uhid='"
							+ uhid.trim() + "' and creationtime >= '" + entryTimeServerFormat
							+ "' and creationtime <= '" + entryEndTimeServerFormat + "' and event = 'Apnea' "
							+ "and eventstatus = 'Active' order by creationtime";
					apneaActiveList = patientDao.getListFromMappedObjNativeQuery(apneaAssessmentQuery);
					if (!BasicUtils.isEmpty(apneaActiveList)) {
						isApnea = true;
					}
				}

				if (isApnea) {
					assesmentsList.add("Apnea");
				}

				// Respiratory Distress
				boolean isRespiratoryDistress = false;
				String respiratoryDistressAssessmentQuery = "SELECT obj FROM VwAssesmentRespsystemFinal as obj where uhid='"
						+ uhid.trim() + "' and creationtime <= '" + entryTimeServerFormat
						+ "' and event = 'Respiratory Distress' order by creationtime desc";
				List<VwAssesmentRespsystemFinal> respiratoryDistressActiveList = patientDao
						.getListFromMappedObjNativeQuery(respiratoryDistressAssessmentQuery);
				if (!BasicUtils.isEmpty(respiratoryDistressActiveList)) {
					if (respiratoryDistressActiveList.get(0) != null && respiratoryDistressActiveList.get(0).getEventstatus().equalsIgnoreCase("Active")) {
						isRespiratoryDistress = true;
					}
				}

				if (!isPphn) {
					respiratoryDistressAssessmentQuery = "SELECT obj FROM VwAssesmentRespsystemFinal as obj where uhid='"
							+ uhid.trim() + "' and creationtime >= '" + entryTimeServerFormat
							+ "' and creationtime <= '" + entryEndTimeServerFormat + "' and event = 'Respiratory Distress' "
							+ "and eventstatus = 'Active' order by creationtime";
					respiratoryDistressActiveList = patientDao.getListFromMappedObjNativeQuery(respiratoryDistressAssessmentQuery);
					if (!BasicUtils.isEmpty(respiratoryDistressActiveList)) {
						isRespiratoryDistress = true;
					}
				}

				if (isRespiratoryDistress) {
					assesmentsList.add("RDS");
				}

				//Jaundice
				boolean isJaundice = false;
				String jaundiceAssessmentQuery = "SELECT obj FROM SaJaundice as obj where uhid='"
						+ uhid.trim() + "' and assessment_time <= '" + entryTimeServerFormat
						+ "' order by assessment_time desc";
				List<SaJaundice> jaundiceActiveList = patientDao.getListFromMappedObjNativeQuery(jaundiceAssessmentQuery);
				if (!BasicUtils.isEmpty(jaundiceActiveList)) {
					if (jaundiceActiveList.get(0) != null && jaundiceActiveList.get(0).getJaundicestatus().equalsIgnoreCase("Yes")) {
						isJaundice = true;
					}
				}

				jaundiceAssessmentQuery = "SELECT obj FROM SaJaundice as obj where uhid='" + uhid.trim()
						+ "' and assessment_time >= '" + entryTimeServerFormat
						+ "' and assessment_time <= '" + entryEndTimeServerFormat
						+ "' order by assessment_time";
				jaundiceActiveList = patientDao.getListFromMappedObjNativeQuery(jaundiceAssessmentQuery);
				if (!BasicUtils.isEmpty(jaundiceActiveList)) {
					isJaundice = true;
				}

				if (isJaundice) {
					assesmentsList.add("Jaundice");
				}

				//Sepsis
				boolean isSepsis = false;
				String sepsisAssessmentQuery = "SELECT obj FROM SaSepsis as obj where uhid='"
						+ uhid.trim() + "' and assessment_time <= '" + entryTimeServerFormat
						+ "' order by assessment_time desc";
				List<SaSepsis> sepsisActiveList = patientDao.getListFromMappedObjNativeQuery(sepsisAssessmentQuery);
				if (!BasicUtils.isEmpty(sepsisActiveList)) {
					if (sepsisActiveList.get(0) != null && sepsisActiveList.get(0).getEventstatus().equalsIgnoreCase("Yes")) {
						isSepsis = true;
					}
				}

				sepsisAssessmentQuery = "SELECT obj FROM SaSepsis as obj where uhid='" + uhid.trim()
						+ "' and assessment_time >= '" + entryTimeServerFormat
						+ "' and assessment_time <= '" + entryEndTimeServerFormat
						+ "' order by assessment_time";
				sepsisActiveList = patientDao.getListFromMappedObjNativeQuery(sepsisAssessmentQuery);
				if (!BasicUtils.isEmpty(sepsisActiveList)) {
					isSepsis = true;
				}

				if (isSepsis) {
					assesmentsList.add("Sepsis");
				}

				//NEC
				boolean isNec = false;
				String necAssessmentQuery = "SELECT obj FROM SaNec as obj where uhid='"
						+ uhid.trim() + "' and assessment_time <= '" + entryTimeServerFormat
						+ "' order by assessment_time desc";
				List<SaNec> necActiveList = patientDao.getListFromMappedObjNativeQuery(necAssessmentQuery);
				if (!BasicUtils.isEmpty(necActiveList)) {
					if (necActiveList.get(0) != null && necActiveList.get(0).getEventstatus().equalsIgnoreCase("Yes")) {
						isNec = true;
					}
				}

				necAssessmentQuery = "SELECT obj FROM SaNec as obj where uhid='" + uhid.trim()
						+ "' and assessment_time >= '" + entryTimeServerFormat
						+ "' and assessment_time <= '" + entryEndTimeServerFormat
						+ "' order by assessment_time";
				necActiveList = patientDao.getListFromMappedObjNativeQuery(necAssessmentQuery);
				if (!BasicUtils.isEmpty(necActiveList)) {
					isNec = true;
				}

				if (isNec) {
					assesmentsList.add("NEC");
				}

				//Asphyxia
				boolean isAsphyxia = false;
				String asphyxiaAssessmentQuery = "SELECT obj FROM VwAssesmentCnsFinal as obj where uhid='"
						+ uhid.trim() + "' and creationtime <= '" + entryTimeServerFormat
						+ "' and event = 'Asphyxia' order by creationtime desc";
				List<VwAssesmentCnsFinal> asphyxiaActiveList = patientDao
						.getListFromMappedObjNativeQuery(asphyxiaAssessmentQuery);

				if (!BasicUtils.isEmpty(asphyxiaActiveList)) {
					if (asphyxiaActiveList.get(0) != null && asphyxiaActiveList.get(0).getEventstatus().equalsIgnoreCase("yes")) {
						isAsphyxia = true;
					}
				}

				if (!isPphn) {
					asphyxiaAssessmentQuery = "SELECT obj FROM VwAssesmentCnsFinal as obj where uhid='" + uhid.trim()
							+ "' and creationtime >= '" + entryTimeServerFormat + "' and creationtime <= '"
							+ entryEndTimeServerFormat + "' and event = 'Asphyxia' order by creationtime";
					asphyxiaActiveList = patientDao.getListFromMappedObjNativeQuery(asphyxiaAssessmentQuery);
					if (!BasicUtils.isEmpty(asphyxiaActiveList)) {
						isAsphyxia = true;
					}
				}

				if (isAsphyxia) {
					assesmentsList.add("Asphyxia");
				}

				//Seizures
				boolean isSeizures = false;
				String seizuresAssessmentQuery = "SELECT obj FROM VwAssesmentCnsFinal as obj where uhid='"
						+ uhid.trim() + "' and creationtime <= '" + entryTimeServerFormat
						+ "' and event = 'Seizures' order by creationtime desc";
				List<VwAssesmentCnsFinal> seizuresActiveList = patientDao
						.getListFromMappedObjNativeQuery(seizuresAssessmentQuery);

				if (!BasicUtils.isEmpty(seizuresActiveList)) {
					if (seizuresActiveList.get(0) != null && seizuresActiveList.get(0).getEventstatus().equalsIgnoreCase("yes")) {
						isSeizures = true;
					}
				}

				if (!isPphn) {
					seizuresAssessmentQuery = "SELECT obj FROM VwAssesmentCnsFinal as obj where uhid='" + uhid.trim()
							+ "' and creationtime >= '" + entryTimeServerFormat + "' and creationtime <= '"
							+ entryEndTimeServerFormat + "' and event = 'Seizures' order by creationtime";
					seizuresActiveList = patientDao.getListFromMappedObjNativeQuery(seizuresAssessmentQuery);
					if (!BasicUtils.isEmpty(seizuresActiveList)) {
						isSeizures = true;
					}
				}

				if (isSeizures) {
					assesmentsList.add("Seizures");
				}

				//Renal
				boolean isRenal = false;
				String renalAssessmentQuery = "SELECT obj FROM SaRenalfailure as obj where uhid='"
						+ uhid.trim() + "' and assessment_time <= '" + entryTimeServerFormat
						+ "' order by assessment_time desc";
				List<SaRenalfailure> renalActiveList = patientDao.getListFromMappedObjNativeQuery(renalAssessmentQuery);
				if (!BasicUtils.isEmpty(renalActiveList)) {
					if (renalActiveList.get(0) != null && renalActiveList.get(0).getRenalstatus().equalsIgnoreCase("Yes")) {
						isRenal = true;
					}
				}

				renalAssessmentQuery = "SELECT obj FROM SaRenalfailure as obj where uhid='" + uhid.trim()
						+ "' and assessment_time >= '" + entryTimeServerFormat
						+ "' and assessment_time <= '" + entryEndTimeServerFormat
						+ "' order by assessment_time";
				renalActiveList = patientDao.getListFromMappedObjNativeQuery(renalAssessmentQuery);
				if (!BasicUtils.isEmpty(renalActiveList)) {
					isRenal = true;
				}

				if (isRenal) {
					assesmentsList.add("Renal");
				}

				// Hypoglycemia
				boolean isHypoglycemia = false;
				String hypoglycemiaAssessmentQuery = "SELECT obj FROM SaHypoglycemia as obj where uhid='"
						+ uhid.trim() + "' and assessment_time <= '" + entryTimeServerFormat
						+ "' order by assessment_time desc";
				List<SaHypoglycemia> hypoglycemiaActiveList = patientDao.getListFromMappedObjNativeQuery(hypoglycemiaAssessmentQuery);
				if (!BasicUtils.isEmpty(hypoglycemiaActiveList)) {
					if (hypoglycemiaActiveList.get(0) != null && hypoglycemiaActiveList.get(0).getHypoglycemiaEventStatus().equalsIgnoreCase("Yes")) {
						isHypoglycemia = true;
					}
				}

				hypoglycemiaAssessmentQuery = "SELECT obj FROM SaHypoglycemia as obj where uhid='" + uhid.trim()
						+ "' and assessment_time >= '" + entryTimeServerFormat
						+ "' and assessment_time <= '" + entryEndTimeServerFormat
						+ "' order by assessment_time";
				hypoglycemiaActiveList = patientDao.getListFromMappedObjNativeQuery(hypoglycemiaAssessmentQuery);
				if (!BasicUtils.isEmpty(hypoglycemiaActiveList)) {
					isHypoglycemia = true;
				}

				if (isHypoglycemia) {
					assesmentsList.add("Hypoglycemia");
				}

				//Feed Intolerance
				boolean isFeedIntolerance = false;
				String feedIntoleranceAssessmentQuery = "SELECT obj FROM SaFeedIntolerance as obj where uhid='"
						+ uhid.trim() + "' and assessment_time <= '" + entryTimeServerFormat
						+ "' order by assessment_time desc";
				List<SaFeedIntolerance> feedIntoleranceActiveList = patientDao.getListFromMappedObjNativeQuery(feedIntoleranceAssessmentQuery);
				if (!BasicUtils.isEmpty(feedIntoleranceActiveList)) {
					if (feedIntoleranceActiveList.get(0) != null && feedIntoleranceActiveList.get(0).getFeedIntoleranceStatus().equalsIgnoreCase("Yes")) {
						isFeedIntolerance = true;
					}
				}

				feedIntoleranceAssessmentQuery = "SELECT obj FROM SaFeedIntolerance as obj where uhid='" + uhid.trim()
						+ "' and assessment_time >= '" + entryTimeServerFormat
						+ "' and assessment_time <= '" + entryEndTimeServerFormat
						+ "' order by assessment_time";
				feedIntoleranceActiveList = patientDao.getListFromMappedObjNativeQuery(feedIntoleranceAssessmentQuery);
				if (!BasicUtils.isEmpty(feedIntoleranceActiveList)) {
					isFeedIntolerance = true;
				}

				if (isFeedIntolerance) {
					assesmentsList.add("Feed Intolerance");
				}

				activeFilterGraphDataObj.setAssesmentsList(assesmentsList);


				//Medication
				List<String> medicationListAntibiotics = new ArrayList<>();
				List<String> medicationListOthers = new ArrayList<>();

				String medicationAssessmentQuery = "SELECT obj FROM BabyPrescription as obj where uhid='"
						+ uhid.trim() + "' and medicineorderdate <= '" + entryEndTimeServerFormat
						+ "' and isactive = 'false' and enddate is not null and "
						+ " isedit is null and iscontinuemedication is null order by medicineorderdate asc";
				List<BabyPrescription> medicationActiveList = patientDao
						.getListFromMappedObjNativeQuery(medicationAssessmentQuery);
				for (BabyPrescription medicationActiveListObj : medicationActiveList) {
					if (!BasicUtils.isEmpty(medicationActiveListObj.getMedicinename())) {

						if (medicationActiveListObj.getMedicationtype().equalsIgnoreCase("TYPE0001")) {
							if (!medicationListAntibiotics.contains(medicationActiveListObj.getMedicinename())) {
								medicationListAntibiotics.add(medicationActiveListObj.getMedicinename());
							}
						} else {
							if (!medicationListOthers.contains(medicationActiveListObj.getMedicinename())) {
								medicationListOthers.add(medicationActiveListObj.getMedicinename());
							}
						}
					}
				}

				medicationAssessmentQuery = "SELECT obj FROM BabyPrescription as obj where uhid='"
						+ uhid.trim() + "' and medicineorderdate <= '" + entryEndTimeServerFormat
						+ "' and isactive = 'true' and enddate is null order by medicineorderdate asc";
				medicationActiveList = patientDao.getListFromMappedObjNativeQuery(medicationAssessmentQuery);

				for (BabyPrescription medicationActiveListObj : medicationActiveList) {
					if (!BasicUtils.isEmpty(medicationActiveListObj.getMedicinename())) {

						if (medicationActiveListObj.getMedicationtype().equalsIgnoreCase("TYPE0001")) {
							if (!medicationListAntibiotics.contains(medicationActiveListObj.getMedicinename())) {
								medicationListAntibiotics.add(medicationActiveListObj.getMedicinename());
							}
						} else {
							if (!medicationListOthers.contains(medicationActiveListObj.getMedicinename())) {
								medicationListOthers.add(medicationActiveListObj.getMedicinename());
							}
						}
					}
				}

				activeFilterGraphDataObj.setMedicationListAntibiotics(medicationListAntibiotics);
				activeFilterGraphDataObj.setMedicationListOthers(medicationListOthers);

				//Procedures
				List<String> proceduresList = new ArrayList<>();
				String procedureAssessmentQuery = "SELECT b.procedure_type, b.uhid, b.starttime, b.endtime FROM vw_procedures_usage as b where b.uhid='"
						+ uhid.trim() + "' and b.starttime <= '" + entryEndTimeServerFormat
						+ "' and b.endtime is not null order by b.starttime asc";
				List<Object[]> procedureActiveList = patientDao.getListFromNativeQuery(procedureAssessmentQuery);
				for (Object[] procedureActiveListObj : procedureActiveList) {
					if (!BasicUtils.isEmpty((String) procedureActiveListObj[0])) {
						if (!proceduresList.contains((String) procedureActiveListObj[0])) {
							proceduresList.add((String) procedureActiveListObj[0]);
						}
					}
				}

				procedureAssessmentQuery = "SELECT b.procedure_type, b.uhid, b.starttime, b.endtime FROM vw_procedures_usage as b where b.uhid='"
						+ uhid.trim() + "' and b.starttime <= '" + entryEndTimeServerFormat
						+ "' and b.endtime is null order by b.starttime asc";
				procedureActiveList = patientDao.getListFromNativeQuery(procedureAssessmentQuery);
				for (Object[] procedureActiveListObj : procedureActiveList) {
					if (!BasicUtils.isEmpty((String) procedureActiveListObj[0])) {
						if (!proceduresList.contains((String) procedureActiveListObj[0])) {
							proceduresList.add((String) procedureActiveListObj[0]);
						}
					}
				}

				activeFilterGraphDataObj.setProceduresList(proceduresList);

				//Physiological(Monitor)
				List<String> monitorList = new ArrayList<>();
				String queryForHR = "SELECT count(*) FROM DeviceMonitorDetail WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
						+ "' AND starttime <= '" + entryEndTimeServerFormat + "' and heartrate != NULL";
				List<Long> hrList = patientDao
						.getListFromMappedObjNativeQuery(queryForHR);
				if (!BasicUtils.isEmpty(hrList)) {
					if (hrList.get(0) > 0) {
						monitorList.add("HR");
					}
				}

				String queryForSpo2 = "SELECT count(*) FROM DeviceMonitorDetail WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
						+ "' AND starttime <= '" + entryEndTimeServerFormat + "' and spo2 != NULL";
				List<Long> spo2List = patientDao
						.getListFromMappedObjNativeQuery(queryForSpo2);
				if (!BasicUtils.isEmpty(spo2List)) {
					if (spo2List.get(0) > 0) {
						monitorList.add("Spo2");
					}
				}

				String queryForPR = "SELECT count(*) FROM DeviceMonitorDetail WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
						+ "' AND starttime <= '" + entryEndTimeServerFormat + "' and pulserate != NULL";
				List<Long> prList = patientDao
						.getListFromMappedObjNativeQuery(queryForPR);
				if (!BasicUtils.isEmpty(prList)) {
					if (prList.get(0) > 0) {
						monitorList.add("PR");
					}
				}

				String queryForRR = "SELECT count(*) FROM DeviceMonitorDetail WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
						+ "' AND starttime <= '" + entryEndTimeServerFormat + "' and ecg_resprate != NULL";
				List<Long> rrList = patientDao
						.getListFromMappedObjNativeQuery(queryForRR);
				if (!BasicUtils.isEmpty(rrList)) {
					if (rrList.get(0) > 0) {
						monitorList.add("RR");
					}
				}

				String queryForRenalSpo2 = "SELECT count(*) FROM DeviceMonitorDetail WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
						+ "' AND starttime <= '" + entryEndTimeServerFormat + "' and o3rso2_1 != NULL";
				List<Long> renalSpo2List = patientDao
						.getListFromMappedObjNativeQuery(queryForRenalSpo2);
				if (!BasicUtils.isEmpty(renalSpo2List)) {
					if (renalSpo2List.get(0) > 0) {
						monitorList.add("Renal Spo2");
					}
				}

				String queryForCerebralSpo2 = "SELECT count(*) FROM DeviceMonitorDetail WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
						+ "' AND starttime <= '" + entryEndTimeServerFormat + "' and o3rso2_2 != NULL";
				List<Long> cerebralSpo2List = patientDao
						.getListFromMappedObjNativeQuery(queryForCerebralSpo2);
				if (!BasicUtils.isEmpty(cerebralSpo2List)) {
					if (cerebralSpo2List.get(0) > 0) {
						monitorList.add("Cerebral Spo2");
					}
				}

				String queryForPI = "SELECT count(*) FROM DeviceMonitorDetail WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
						+ "' AND starttime <= '" + entryEndTimeServerFormat + "' and pi != NULL";
				List<Long> piList = patientDao
						.getListFromMappedObjNativeQuery(queryForPI);
				if (!BasicUtils.isEmpty(piList)) {
					if (piList.get(0) > 0) {
						monitorList.add("PI");
					}
				}


				// NIBP -s
				String queryForNbp_s = "SELECT count(*) FROM DeviceMonitorDetail WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
						+ "' AND starttime <= '" + entryEndTimeServerFormat + "' and nbp_s is not null";
				List<Long> nbp_s = patientDao
						.getListFromMappedObjNativeQuery(queryForNbp_s);
				if (!BasicUtils.isEmpty(nbp_s)) {
					if (nbp_s.get(0) > 0) {
						monitorList.add("NIBP Syst");
					}
				}

				// NIBP - d
				String queryForNbp_d = "SELECT count(*) FROM DeviceMonitorDetail WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
						+ "' AND starttime <= '" + entryEndTimeServerFormat + "' and nbp_d is not null";
				List<Long> nbp_d = patientDao
						.getListFromMappedObjNativeQuery(queryForNbp_d);
				if (!BasicUtils.isEmpty(nbp_d)) {
					if (nbp_d.get(0) > 0) {
						monitorList.add("NIBP Diast");
					}
				}

				// NIBP -  m
				String queryForNbp_m = "SELECT count(*) FROM DeviceMonitorDetail WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
						+ "' AND starttime <= '" + entryEndTimeServerFormat + "' and nbp_m is not null";
				List<Long> nbp_m = patientDao
						.getListFromMappedObjNativeQuery(queryForNbp_m);
				if (!BasicUtils.isEmpty(nbp_m)) {
					if (nbp_m.get(0) > 0) {
						monitorList.add("NIBP Mean");
					}
				}

				// IBP -s
				String queryForIbp_s = "SELECT count(*) FROM DeviceMonitorDetail WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
						+ "' AND starttime <= '" + entryEndTimeServerFormat + "' and ibp_s is not null";
				List<Long> ibp_s = patientDao
						.getListFromMappedObjNativeQuery(queryForIbp_s);
				if (!BasicUtils.isEmpty(ibp_s)) {
					if (ibp_s.get(0) > 0) {
						monitorList.add("IBP Syst");
					}
				}

				// IBP - d
				String queryForIbp_d = "SELECT count(*) FROM DeviceMonitorDetail WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
						+ "' AND starttime <= '" + entryEndTimeServerFormat + "' and ibp_d is not null";
				List<Long> ibp_d = patientDao
						.getListFromMappedObjNativeQuery(queryForIbp_d);
				if (!BasicUtils.isEmpty(ibp_d)) {
					if (ibp_d.get(0) > 0) {
						monitorList.add("IBP Diast");
					}
				}

				// IBP -  m
				String queryForIbp_m = "SELECT count(*) FROM DeviceMonitorDetail WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
						+ "' AND starttime <= '" + entryEndTimeServerFormat + "' and ibp_m is not null";
				List<Long> ibp_m = patientDao
						.getListFromMappedObjNativeQuery(queryForIbp_m);
				if (!BasicUtils.isEmpty(ibp_m)) {
					if (ibp_m.get(0) > 0) {
						monitorList.add("IBP Mean");
					}
				}
				activeFilterGraphDataObj.setMonitorList(monitorList);

				//Physiological(Ventilator)
				List<String> ventilatorList = new ArrayList<>();
				String queryForFIO2 = "SELECT count(*) FROM DeviceVentilatorData WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND start_time >= '" + entryTimeServerFormat
						+ "' AND start_time <= '" + entryEndTimeServerFormat + "' and fio2 != NULL";
				List<Long> fio2List = patientDao
						.getListFromMappedObjNativeQuery(queryForFIO2);
				if (!BasicUtils.isEmpty(fio2List)) {
					if (fio2List.get(0) > 0) {
						ventilatorList.add("FIO2");
					}
				}

				String queryForPIP = "SELECT count(*) FROM DeviceVentilatorData WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND start_time >= '" + entryTimeServerFormat
						+ "' AND start_time <= '" + entryEndTimeServerFormat + "' and pip != NULL";
				List<Long> pipList = patientDao
						.getListFromMappedObjNativeQuery(queryForPIP);
				if (!BasicUtils.isEmpty(pipList)) {
					if (pipList.get(0) > 0) {
						ventilatorList.add("PIP");
					}
				}

				String queryForPEEP = "SELECT count(*) FROM DeviceVentilatorData WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND start_time >= '" + entryTimeServerFormat
						+ "' AND start_time <= '" + entryEndTimeServerFormat + "' and peep != NULL";
				List<Long> peepList = patientDao
						.getListFromMappedObjNativeQuery(queryForPEEP);
				if (!BasicUtils.isEmpty(peepList)) {
					if (peepList.get(0) > 0) {
						ventilatorList.add("PEEP");
					}
				}

				String queryForMAP = "SELECT count(*) FROM DeviceVentilatorData WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND start_time >= '" + entryTimeServerFormat
						+ "' AND start_time <= '" + entryEndTimeServerFormat + "' and map != NULL";
				List<Long> mapList = patientDao
						.getListFromMappedObjNativeQuery(queryForMAP);
				if (!BasicUtils.isEmpty(mapList)) {
					if (mapList.get(0) > 0) {
						ventilatorList.add("MAP");
					}
				}

				String queryForFlowRate = "SELECT count(*) FROM DeviceVentilatorData WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND start_time >= '" + entryTimeServerFormat
						+ "' AND start_time <= '" + entryEndTimeServerFormat + "' and flowpermin != NULL";
				List<Long> flowRateList = patientDao
						.getListFromMappedObjNativeQuery(queryForFlowRate);
				if (!BasicUtils.isEmpty(flowRateList)) {
					if (flowRateList.get(0) > 0) {
						ventilatorList.add("Flow Rate");
					}
				}

				activeFilterGraphDataObj.setVentilatorList(ventilatorList);

				//Physiological(Oximeter)
				List<String> oximeterList = new ArrayList<>();
				String queryForChannel1 = "SELECT count(*) FROM DeviceCerebralOximeterDetail WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
						+ "' AND starttime <= '" + entryEndTimeServerFormat + "' and channel1_rso2 != NULL";
				List<Long> channel1List = patientDao
						.getListFromMappedObjNativeQuery(queryForChannel1);
				if (!BasicUtils.isEmpty(channel1List)) {
					if (channel1List.get(0) > 0) {
						oximeterList.add("Channel 1");
					}
				}

				String queryForChannel2 = "SELECT count(*) FROM DeviceCerebralOximeterDetail WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
						+ "' AND starttime <= '" + entryEndTimeServerFormat + "' and channel2_rso2 != NULL";
				List<Long> channel2List = patientDao
						.getListFromMappedObjNativeQuery(queryForChannel2);
				if (!BasicUtils.isEmpty(channel2List)) {
					if (channel2List.get(0) > 0) {
						oximeterList.add("Channel 2");
					}
				}

				String queryForChannel3 = "SELECT count(*) FROM DeviceCerebralOximeterDetail WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
						+ "' AND starttime <= '" + entryEndTimeServerFormat + "' and channel3_rso2 != NULL";
				List<Long> channel3List = patientDao
						.getListFromMappedObjNativeQuery(queryForChannel3);
				if (!BasicUtils.isEmpty(channel3List)) {
					if (channel3List.get(0) > 0) {
						oximeterList.add("Channel 3");
					}
				}

				String queryForChannel4 = "SELECT count(*) FROM DeviceCerebralOximeterDetail WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND starttime >= '" + entryTimeServerFormat
						+ "' AND starttime <= '" + entryEndTimeServerFormat + "' and channel4_rso2 != NULL";
				List<Long> channel4List = patientDao
						.getListFromMappedObjNativeQuery(queryForChannel4);
				if (!BasicUtils.isEmpty(channel4List)) {
					if (channel4List.get(0) > 0) {
						oximeterList.add("Channel 4");
					}
				}

				activeFilterGraphDataObj.setMonitorList(oximeterList);

				//Blood Gas
				String zone_time = "GMT+5:30";
				int offset = TimeZone.getTimeZone(zone_time).getRawOffset()
						- TimeZone.getDefault().getRawOffset();
				System.out.println("offset = " + offset);

				Date entryTimeStampWithOffset = new Date(entryTimeStamp.getTime() + offset);
				Date entryEndTimeStampWithOffset = new Date(entryEndTimeStamp.getTime() + offset);
				String entryTimeServerFormatWithOffset = formatter.format(entryTimeStampWithOffset);
				String entryEndTimeServerFormatWithOffset = formatter.format(entryEndTimeStampWithOffset);
				List<String> bloodGasList = new ArrayList<>();
				String bloodGasAssessmentQuery = "SELECT obj FROM NursingBloodGas as obj where uhid='" + uhid.trim()
						+ "' and entrydate <= '" + entryEndTimeServerFormatWithOffset + "' and entrydate >= '"
						+ entryTimeServerFormatWithOffset + "' order by entrydate";

				List<NursingBloodGas> bloodGasActiveList = patientDao
						.getListFromMappedObjNativeQuery(bloodGasAssessmentQuery);
				if (!BasicUtils.isEmpty(bloodGasActiveList)) {
					for (int i = 0; i < bloodGasActiveList.size(); i++) {
						NursingBloodGas bloodGasActiveListObj = bloodGasActiveList.get(i);
						if (bloodGasActiveListObj.getBe() != null && bloodGasActiveListObj.getBe() != "") {
							if (!bloodGasList.contains(bloodGasActiveListObj.getBe())) {
								bloodGasList.add("be");
							}
						}
						if (bloodGasActiveListObj.getHco2() != null && bloodGasActiveListObj.getHco2() != "") {
							if (!bloodGasList.contains(bloodGasActiveListObj.getHco2())) {
								bloodGasList.add("hco2");
							}
						}
						if (bloodGasActiveListObj.getPco2() != null && bloodGasActiveListObj.getPco2() != "") {
							if (!bloodGasList.contains(bloodGasActiveListObj.getPco2())) {
								bloodGasList.add("pco2");
							}
						}
						if (bloodGasActiveListObj.getPh() != null && bloodGasActiveListObj.getPh() != "") {
							if (!bloodGasList.contains(bloodGasActiveListObj.getPh())) {
								bloodGasList.add("ph");
							}
						}
						if (bloodGasActiveListObj.getPo2() != null && bloodGasActiveListObj.getPo2() != "") {
							if (!bloodGasList.contains(bloodGasActiveListObj.getPo2())) {
								bloodGasList.add("po2");
							}
						}
						if (bloodGasActiveListObj.getLactate() != null && bloodGasActiveListObj.getLactate() != "") {
							if (!bloodGasList.contains(bloodGasActiveListObj.getLactate())) {
								bloodGasList.add("lactate");
							}
						}
						if (bloodGasActiveListObj.getNa() != null && bloodGasActiveListObj.getNa() != "") {
							if (!bloodGasList.contains(bloodGasActiveListObj.getNa())) {
								bloodGasList.add("na");
							}
						}
						if (bloodGasActiveListObj.getGlucose() != null && bloodGasActiveListObj.getGlucose() != "") {
							if (!bloodGasList.contains(bloodGasActiveListObj.getGlucose())) {
								bloodGasList.add("glucose");
							}
						}
						if (bloodGasActiveListObj.getBe_ecf() != null && bloodGasActiveListObj.getBe_ecf() != "") {
							if (!bloodGasList.contains(bloodGasActiveListObj.getBe_ecf())) {
								bloodGasList.add("be_ecf");
							}
						}
						if (bloodGasActiveListObj.getThbc() != null && bloodGasActiveListObj.getThbc() != "") {
							if (!bloodGasList.contains(bloodGasActiveListObj.getThbc())) {
								bloodGasList.add("thbc");
							}
						}
						if (bloodGasActiveListObj.getHct() != null && bloodGasActiveListObj.getHct() != "") {
							if (!bloodGasList.contains(bloodGasActiveListObj.getHct())) {
								bloodGasList.add("hct");
							}
						}
					}
				}

				activeFilterGraphDataObj.setBloodGasList(bloodGasList);

				//  Surfactant usage
				String surfactantQuery = "SELECT obj FROM SaRespRds as obj WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND assessment_time >= '" + entryTimeServerFormat
						+ "' AND assessment_time <= '" + entryEndTimeServerFormat + "' and sufactantname != NULL and surfactant_dose_ml!=NULL order by assessment_time";
				List<SaRespRds> surfactantObjectList = patientDao
						.getListFromMappedObjNativeQuery(surfactantQuery);
				if (!BasicUtils.isEmpty(surfactantObjectList) && surfactantObjectList.size() > 0) {
					activeFilterGraphDataObj.setSurfactantUsage(true);
				}

				// Oxygenation Index (INO)
				String oxygenationIndexQuery = "SELECT obj FROM SaRespPphn as obj WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND assessment_time >= '" + entryTimeServerFormat
						+ "' AND assessment_time <= '" + entryEndTimeServerFormat + "' and oxygenation_index != NULL order by assessment_time";
				List<SaRespPphn> oxygenationObjectList = patientDao
						.getListFromMappedObjNativeQuery(oxygenationIndexQuery);

				if (!BasicUtils.isEmpty(oxygenationObjectList) && oxygenationObjectList.size() > 0) {
					activeFilterGraphDataObj.setOxygenationIndex(true);
				}

				// Phototherapy Duration
				String phototherapyDurationQuery = "SELECT obj FROM SaJaundice as obj WHERE " + "uhid ='"
						+ uhid.trim() + "' " + "AND assessment_time >= '" + entryTimeServerFormat
						+ "' AND assessment_time <= '" + entryEndTimeServerFormat + "' and phototherapyvalue != NULL order by assessment_time";
				List<SaJaundice> phototherapyDurationList = patientDao
						.getListFromMappedObjNativeQuery(phototherapyDurationQuery);

				if (!BasicUtils.isEmpty(phototherapyDurationList)) {
					activeFilterGraphDataObj.setPhototheraphy(true);
				}

				// Enteral Intake
				List<String> enteralIntakeList = new ArrayList<>();
				List<String> parentralIntakeList = new ArrayList<>();
				List<String> urineList = new ArrayList<>();
				List<String> adbList = new ArrayList<>();

				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

					Date parsedDate = dateFormat.parse(entryTimeServerFormat);
					Timestamp fromTimestamp = new java.sql.Timestamp(parsedDate.getTime());

					Date toDate = dateFormat.parse(entryEndTimeServerFormat);
					Timestamp toTimestamp = new java.sql.Timestamp(toDate.getTime());


					String getNurseOrder = HqlSqlQueryConstants.getNursingExecution(uhid, fromTimestamp, toTimestamp);
					List<NursingIntakeOutput> nursingIntakeOutputsList = patientDao.getListFromMappedObjNativeQuery(getNurseOrder);
					if (nursingIntakeOutputsList != null && !BasicUtils.isEmpty(nursingIntakeOutputsList)) {

						for (NursingIntakeOutput nursingIntakeOutputObject : nursingIntakeOutputsList) {

							double totalPreviousEn = 0;
							double totalPreviousPn = 0;

							// Enteral  Intake
							if (nursingIntakeOutputObject.getActualFeed() != null) {
								totalPreviousEn += nursingIntakeOutputObject.getActualFeed();
								totalPreviousEn = Math.round(totalPreviousEn * 100.0) / 100.0;
							}

							// Parenteral  Intake

							if (nursingIntakeOutputObject.getLipid_delivered() != null) {
								totalPreviousPn += (nursingIntakeOutputObject.getLipid_delivered());
							}

							if (nursingIntakeOutputObject.getTpn_delivered() != null) {
								totalPreviousPn += (nursingIntakeOutputObject.getTpn_delivered());
							}

							if (nursingIntakeOutputObject.getReadymadeDeliveredFeed() != null) {
								totalPreviousPn += (nursingIntakeOutputObject.getReadymadeDeliveredFeed());
							}

							if (!BasicUtils.isEmpty(totalPreviousPn)) {
								totalPreviousPn = Math.round(totalPreviousPn * 100.0) / 100.0;
							}

							// Abdominal Girth
							String adbValue = null;
							if (!BasicUtils.isEmpty(nursingIntakeOutputObject.getAbdomenGirth())) {
								adbValue = nursingIntakeOutputObject.getAbdomenGirth();
							}

							// Urine Output
							String urineValue = null;
							if (!BasicUtils.isEmpty(nursingIntakeOutputObject.getUrinePassed()) && nursingIntakeOutputObject.getUrinePassed()) {
								if (!BasicUtils.isEmpty(nursingIntakeOutputObject.getUrine())) {
									urineValue = nursingIntakeOutputObject.getUrine();
								}
							}

							if (totalPreviousEn != 0) {
								enteralIntakeList.add(String.valueOf(totalPreviousEn));
							}

							if (totalPreviousPn != 0) {
								parentralIntakeList.add(String.valueOf(totalPreviousPn));
							}

							if (urineValue != null) {
								urineList.add(urineValue);
							}

							if (adbValue != null) {
								adbList.add(adbValue);
							}
						}

						if (!BasicUtils.isEmpty(enteralIntakeList) && enteralIntakeList.size() > 0) {
							activeFilterGraphDataObj.setEnteralIntake(true);
						}

						if (!BasicUtils.isEmpty(parentralIntakeList) && parentralIntakeList.size() > 0) {
							activeFilterGraphDataObj.setParentralIntake(true);
						}

						if (!BasicUtils.isEmpty(urineList) && urineList.size() > 0) {
							activeFilterGraphDataObj.setUrineOutput(true);
						}

						if (!BasicUtils.isEmpty(adbList) && adbList.size() > 0) {
							activeFilterGraphDataObj.setAbdOutput(true);
						}
					}

					// Events
					List<String> events = new ArrayList<>();
					String nursingEpisodeQuery = HqlSqlQueryConstants.getNursingEpisodeListForChart(uhid, fromTimestamp, toTimestamp);
					List<NursingEpisode> nursingEpisodeList = patientDao.getListFromMappedObjNativeQuery(nursingEpisodeQuery);

					if (!BasicUtils.isEmpty(nursingEpisodeList) && nursingEpisodeList.size() > 0) {
						for (NursingEpisode nursingEpisodeObject : nursingEpisodeList) {

							String eventsHappened = "";

							if (!BasicUtils.isEmpty(nursingEpisodeObject.getSeizures()) && nursingEpisodeObject.getSeizures() == true) {
								if (nursingEpisodeObject.getSeizureDuration() != null && nursingEpisodeObject.getDuration_unit_seizure() != null) {
									events.add("Seizures");
								}
							}


							// Check for Apnea
							if (!BasicUtils.isEmpty(nursingEpisodeObject.getApnea()) && nursingEpisodeObject.getApnea() == true) {
								if (nursingEpisodeObject.getApneaDuration() != null && nursingEpisodeObject.getDuration_unit_apnea() != null) {
									events.add("Apnea");
								}
							}

							// check for desaturation
							if (!BasicUtils.isEmpty(nursingEpisodeObject.getDesaturation()) && nursingEpisodeObject.getDesaturation() == true) {
								if (nursingEpisodeObject.getDesaturationSpo2() != null) {
									events.add("Desaturation");
								}
							}
						}
						if (!BasicUtils.isEmpty(events) && events.size() > 0) {
							activeFilterGraphDataObj.setEvents(true);
						}
					}

				} catch (Exception e) { //this generic but you can control another types of exception
					System.out.println("Exception thrown is :" + e);
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activeFilterGraphDataObj;
	}

	@SuppressWarnings({"unchecked", "deprecation"})
	@Override
	public List<CameraImageStream> getCameraImageTrends(String uhid, Timestamp deviceTime, Boolean flag) {
		List<CameraImageStream> cameraImageTrendsList = new ArrayList<>();
		try {
			Date deviceTimeInDate = new Date(deviceTime.getTime());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

			Date afterDate = new Date(deviceTimeInDate.getTime() + (3 * 24 * 60 * 60000));
			Date beforeDate = new Date(deviceTimeInDate.getTime() - (3 * 24 * 60 * 60000));

			if (flag == true) {
				afterDate = new Date(deviceTimeInDate.getTime() + (10 * 60000));
				beforeDate = new Date(deviceTimeInDate.getTime() - (10 * 60000));
			}

			String deviceTimeInString = (sdf.format(deviceTimeInDate)) + "+0000";
			String timeAfter = (sdf.format(afterDate)) + "+0000";
			String timeBefore = (sdf.format(beforeDate)) + "+0000";

			System.out.println("device time :" + deviceTimeInString);
			System.out.println("time after :" + timeAfter);
			System.out.println("time before :" + timeBefore);

			if (!BasicUtils.isEmpty(uhid) && !BasicUtils.isEmpty(timeBefore) && !BasicUtils.isEmpty(timeAfter)) {
				String cassandraImageSearchQuery = "select * from inicu.camera_imagestream where uhid = '" + uhid.trim() + "' "
						+ "AND device_time >= '" + timeBefore + "'  and device_time < '" + timeAfter + "'";
				List<CameraImageStream> cassandraImageSearchList = cassandraTemplate.select(cassandraImageSearchQuery, CameraImageStream.class);
				if (!BasicUtils.isEmpty(cassandraImageSearchList)) {
					cameraImageTrendsList = cassandraImageSearchList;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cameraImageTrendsList;
	}

	@SuppressWarnings({"unchecked", "deprecation"})
	@Override
	public List<CameraImageStream> getCameraImageTrendsWithPagination(String uhid, Timestamp deviceTime, Boolean flag, Integer pageNumber, Integer pageSize) {
		List<CameraImageStream> cameraImageTrendsList = new ArrayList<>();
		try {
			Date deviceTimeInDate = new Date(deviceTime.getTime());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

			Date afterDate = new Date(deviceTimeInDate.getTime() + (3 * 24 * 60 * 60000));
			Date beforeDate = new Date(deviceTimeInDate.getTime() - (3 * 24 * 60 * 60000));

			if (flag == true) {
				afterDate = new Date(deviceTimeInDate.getTime() + (10 * 60000));
				beforeDate = new Date(deviceTimeInDate.getTime() - (10 * 60000));
			}

			String deviceTimeInString = (sdf.format(deviceTimeInDate)) + "+0000";
			String timeAfter = (sdf.format(afterDate)) + "+0000";
			String timeBefore = (sdf.format(beforeDate)) + "+0000";

			System.out.println("device time :" + deviceTimeInString);
			System.out.println("time after :" + timeAfter);
			System.out.println("time before :" + timeBefore);

			if (!BasicUtils.isEmpty(uhid) && !BasicUtils.isEmpty(timeBefore) && !BasicUtils.isEmpty(timeAfter)) {
				String cassandraImageSearchQuery = "select * from inicu.camera_imagestream where uhid = '" + uhid.trim() + "' "
						+ "AND device_time >= '" + timeBefore + "'  and device_time < '" + timeAfter + "'";
				List<CameraImageStream> cassandraImageSearchList = cassandraTemplate.select(cassandraImageSearchQuery, CameraImageStream.class);
				if (!BasicUtils.isEmpty(cassandraImageSearchList)) {
					Integer totalSize = cassandraImageSearchList.size();
					System.out.println("Total Number of Images = " + totalSize);

					Integer totalPage = totalSize / pageSize;
					System.out.println("Total Number of Pages = " + totalPage);

					if (pageNumber < 0 || pageNumber > totalPage) {
						return cameraImageTrendsList;
					} else {
						Integer startingIndex = (pageNumber) * (pageSize);
						Integer endingIndex = startingIndex + pageSize;
						for (int i = startingIndex; i != endingIndex; i++) {
							cameraImageTrendsList.add(cassandraImageSearchList.get(i));
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cameraImageTrendsList;
	}

	private List<Object[]> fetchItemnameAndValue(Object testname, Object testDate, String uhid) {
		String fetchItemDetails = "SELECT itemname , itemvalue   FROM test_result " + "where prn = '" + uhid
				+ "' and lab_testname='" + testname + "' and lab_report_date='" + testDate
				+ "' and itemname is not null";
		List<Object[]> itemDetails = inicuDao.getListFromNativeQuery(fetchItemDetails);
//	    JSONObject jsonObj = new JSONObject();
//	    for (int i = 0; i < itemDetails.size(); i++) {
//	      try {
//	        if (!BasicUtils.isEmpty(itemDetails.get(i)[1])) {
//	          jsonObj.put((String) itemDetails.get(i)[0], itemDetails.get(i)[1]);
		//
//	        }
//	      } catch (JSONException e) {
//	        e.printStackTrace();
//	      }
//	    }

		return itemDetails;
	}

	@Override
	public NewRegisteredDevicesJSON getRegisteredDevicesNew(String branchName) {
		NewRegisteredDevicesJSON json = new NewRegisteredDevicesJSON();
		List<NewDeviceJSON> registeredDevices = new ArrayList<>();
		String queryToFetchRefInicuBbox = "select DISTINCT(obj) from RefInicuBbox as obj where branchname='" + branchName + "' and active='true'"
				+ " order by creationtime";
		List<RefInicuBbox> refInicuBboxList = patientDao.getListFromMappedObjNativeQuery(queryToFetchRefInicuBbox);
		List<BoxJSON> boxList = new ArrayList<BoxJSON>();
		if (!BasicUtils.isEmpty(refInicuBboxList)) {
			for (RefInicuBbox refInicuBboxObj : refInicuBboxList) {
				BoxJSON box = new BoxJSON();
				if (!BasicUtils.isEmpty(refInicuBboxObj.getBboxname())) {
					box.setBox_name(refInicuBboxObj.getBboxname());
				} else {
					box.setBox_name(refInicuBboxObj.getTinyboxname());
					box.setTinyNeo(true);
				}

				DeviceDetail deviceDetailObj = new DeviceDetail();
				BoardDetailJson board = new BoardDetailJson();
				String serialNum = null;
				//For Board 1
				String queryToFetchDeviceDetailForA = "select obj from DeviceDetail as obj "
						+ "where inicu_deviceid='" + refInicuBboxObj.getBboxId() + "' and branchname='" + branchName + "'"
						+ "and description='SBC-A'";
				List<DeviceDetail> deviceDetailListForA = patientDao.getListFromMappedObjNativeQuery(queryToFetchDeviceDetailForA);
				if (!BasicUtils.isEmpty(deviceDetailListForA)) {
					deviceDetailObj = deviceDetailListForA.get(0);
					board = new BoardDetailJson();
					board.setBoardName(deviceDetailObj.getDescription());
					board.setDeviceType(deviceDetailObj.getDevicetype());
					board.setBrandName(deviceDetailObj.getDevicebrand());
					board.setDeviceName(deviceDetailObj.getDevicename());
					String queryToFetchCommTypeDetail = "select * from ref_inicu_devices "
							+ "where devicename='" + board.getDeviceName() + "'";
					List<Object[]> refInicuDevicesList = patientDao.getListFromNativeQuery(queryToFetchCommTypeDetail);
					if (!BasicUtils.isEmpty(refInicuDevicesList)) {
						board.setCommunicationtype((refInicuDevicesList.get(0)[4]).toString());
					}
					board.setUsbport(deviceDetailObj.getUsbport());
					board.setBaudrate(deviceDetailObj.getBaudrate());
					board.setParity(deviceDetailObj.getParity());
					board.setDatabits(deviceDetailObj.getDatabits());
					board.setStopbits(deviceDetailObj.getStopbits());
					box.setBoard1(board);
					if (!BasicUtils.isEmpty(deviceDetailObj.getDeviceserialno())) {
						serialNum = deviceDetailObj.getDeviceserialno();
					} else {
						serialNum = deviceDetailObj.getTinydeviceserialno();
					}
				}

				//For Board 2
				String queryToFetchDeviceDetailForB = "select obj from DeviceDetail as obj "
						+ "where inicu_deviceid='" + refInicuBboxObj.getBboxId() + "' and branchname='" + branchName + "'"
						+ "and description='SBC-B'";
				List<DeviceDetail> deviceDetailListForB = patientDao.getListFromMappedObjNativeQuery(queryToFetchDeviceDetailForB);
				if (!BasicUtils.isEmpty(deviceDetailListForB)) {
					deviceDetailObj = deviceDetailListForB.get(0);
					board = new BoardDetailJson();
					board.setBoardName(deviceDetailObj.getDescription());
					board.setDeviceType(deviceDetailObj.getDevicetype());
					board.setBrandName(deviceDetailObj.getDevicebrand());
					board.setDeviceName(deviceDetailObj.getDevicename());
					String queryToFetchCommTypeDetail = "select * from ref_inicu_devices "
							+ "where devicename='" + board.getDeviceName() + "'";
					List<Object[]> refInicuDevicesList = patientDao.getListFromNativeQuery(queryToFetchCommTypeDetail);
					if (!BasicUtils.isEmpty(refInicuDevicesList)) {
						board.setCommunicationtype((refInicuDevicesList.get(0)[4]).toString());
					}
					board.setUsbport(deviceDetailObj.getUsbport());
					board.setBaudrate(deviceDetailObj.getBaudrate());
					board.setParity(deviceDetailObj.getParity());
					board.setDatabits(deviceDetailObj.getDatabits());
					board.setStopbits(deviceDetailObj.getStopbits());
					box.setBoard2(board);
					if (!BasicUtils.isEmpty(deviceDetailObj.getDeviceserialno())) {
						serialNum = deviceDetailObj.getDeviceserialno();
					} else {
						serialNum = deviceDetailObj.getTinydeviceserialno();
					}
				}

				box.setBox_serial(serialNum);
				if (box.getBoard1() != null || box.getBoard2() != null) {
					boxList.add(box);
				}
			}
		}

		json.setRegisteredDevices(boxList);

		// setting up dropdowns
		RegisterDeviceDropdownJSon dropdown = new RegisterDeviceDropdownJSon();
		HashMap<String, List<String>> deviceBrandName = new HashMap<>();
		String fetchdevTypeBrand = "SELECT obj FROM InicuDevice as obj";
		List<InicuDevice> result = patientDao.getListFromMappedObjNativeQuery(fetchdevTypeBrand);
		if (!BasicUtils.isEmpty(result)) {
			for (InicuDevice dev : result) {
				if (!BasicUtils.isEmpty(deviceBrandName.get(dev.getBrandname()))) {
					List<String> listDev = deviceBrandName.get(dev.getBrandname());
					listDev.add(dev.getDevicename());
					deviceBrandName.put(dev.getBrandname(), listDev);
				} else {
					List<String> listDevices = new ArrayList<>();
					listDevices.add(dev.getDevicename());
					deviceBrandName.put(dev.getBrandname(), listDevices);
				}
			}
		}

		dropdown.setDeviceBrandName(deviceBrandName);

		HashMap<String, List<String>> deviceTypeBrand = new HashMap<>();
		String fetchDevRef = "select devtype.device_type, " + "dev.brandname " + "from ref_device_type as devtype "
				+ "left outer join " + "ref_inicu_devices as dev  " + "on (devtype.devicetypeid = dev.devicetypeid)";
		List<Object[]> resultDevRef = deviceDAO.executeNativeQuery(fetchDevRef);
		if (!BasicUtils.isEmpty(resultDevRef)) {
			for (Object[] obj : resultDevRef) {
				if (!BasicUtils.isEmpty(deviceTypeBrand.get(obj[0].toString()))) {
					List<String> listDev = deviceTypeBrand.get(obj[0].toString());
					if (!BasicUtils.isEmpty(obj[1])) {
						if (!listDev.contains(obj[1].toString())) {
							listDev.add(obj[1].toString());
						}
					}
					deviceTypeBrand.put(obj[0].toString(), listDev);
				} else {
					List<String> listDevices = new ArrayList<>();
					if (!BasicUtils.isEmpty(obj[1])) {
						listDevices.add(obj[1].toString());
					}
					deviceTypeBrand.put(obj[0].toString(), listDevices);
				}
			}
		}
		dropdown.setDeviceTypeBrand(deviceTypeBrand);

		List<String> deviceCommunicationType = new ArrayList<>();
		String queryToFetchCommunicationType = "SELECT obj FROM InicuDevice as obj";
		List<InicuDevice> communicationTypeList = patientDao.getListFromMappedObjNativeQuery(queryToFetchCommunicationType);
		if (!BasicUtils.isEmpty(communicationTypeList)) {
			for (InicuDevice device : communicationTypeList) {
				if (!BasicUtils.isEmpty(device.getCommunicationtype())
						&& !deviceCommunicationType.contains(device.getCommunicationtype())) {
					deviceCommunicationType.add(device.getCommunicationtype());
				}
			}
		}
		dropdown.setDeviceCommunicationType(deviceCommunicationType);

		List<String> deviceUsbPort = new ArrayList<>();
		deviceUsbPort.add("/dev/ttyS0");
		deviceUsbPort.add("/dev/ttyS1");
		deviceUsbPort.add("/dev/ttyS2");
		deviceUsbPort.add("/dev/ttyS3");
		deviceUsbPort.add("/dev/ttyS4");
		deviceUsbPort.add("/dev/ttyUSB0");
		deviceUsbPort.add("/dev/ttyUSB1");
		dropdown.setDeviceUsbPort(deviceUsbPort);

		List<String> deviceBaudRate = new ArrayList<>();
		deviceBaudRate.add("300");
		deviceBaudRate.add("600");
		deviceBaudRate.add("1200");
		deviceBaudRate.add("2400");
		deviceBaudRate.add("4800");
		deviceBaudRate.add("9600");
		deviceBaudRate.add("19200");
		deviceBaudRate.add("38400");
		deviceBaudRate.add("57600");
		deviceBaudRate.add("115200");
		deviceBaudRate.add("230400");
		deviceBaudRate.add("460800");
		deviceBaudRate.add("576000");
		deviceBaudRate.add("927600");
		deviceBaudRate.add("1000000");
		deviceBaudRate.add("2000000");
		dropdown.setDeviceBaudRate(deviceBaudRate);

		List<String> deviceParity = new ArrayList<>();
		deviceParity.add("None");
		deviceParity.add("Odd");
		deviceParity.add("Even");
//		deviceParity.add("Mark");
//		deviceParity.add("Space");
		dropdown.setDeviceParity(deviceParity);


		List<String> deviceDataBits = new ArrayList<>();
		deviceDataBits.add("5");
		deviceDataBits.add("6");
		deviceDataBits.add("7");
		deviceDataBits.add("8");
		dropdown.setDeviceDataBits(deviceDataBits);

		List<String> deviceStopBits = new ArrayList<>();
		deviceStopBits.add("1");
		deviceStopBits.add("2");
		dropdown.setDeviceStopBits(deviceStopBits);

		json.setDropDowns(dropdown);

		return json;
	}

	@Override
	public RegisterDeviceMasterJson getRegisteredDevice() {
		RegisterDeviceMasterJson json = new RegisterDeviceMasterJson();
		List<RegisteredDevicesJson> registeredDevices = new ArrayList<>();
		String query = "select dd.creationtime, " + "dd.ipaddress, " + "dd.available, " + "id.devicename, "
				+ "id.brandname, " + "dt.device_type, " + "dd.description, " + "dd.usbport, " + "dd.baudrate, "
				+ "dd.parity, " + "dd.databits, " + "dd.stopbits " + "from device_detail as dd,  "
				+ "ref_inicu_devices as id,  " + "ref_device_type as dt " + "where id.deviceid = dd.inicu_deviceid "
				+ "and id.devicetypeid = dt.devicetypeid order by dd.creationtime";

		List<Object[]> resultSet = deviceDAO.executeNativeQuery(query);
		if (!BasicUtils.isEmpty(resultSet)) {
			for (Object[] obj : resultSet) {
				RegisteredDevicesJson dev = new RegisteredDevicesJson();
				if (!BasicUtils.isEmpty(obj[0])) {
					String creationtime = obj[0].toString();
					// String creationtime = "2017-05-15 17:05:37.440942+00:00";
					if (!BasicUtils.isEmpty(creationtime)) {
						dev.setAddedon(creationtime);
					}
				}
				if (!BasicUtils.isEmpty(obj[1])) {
					String macid = obj[1].toString();
					if (!BasicUtils.isEmpty(macid)) {
						dev.setMacid(macid);
					}
				}
				if (!BasicUtils.isEmpty(obj[2])) {
					Boolean available = (Boolean) obj[2];
					if (!BasicUtils.isEmpty(available)) {

					}
				}
				if (!BasicUtils.isEmpty(obj[3])) {
					String deviceName = obj[3].toString();
					if (!BasicUtils.isEmpty(deviceName)) {
						dev.setDeviceName(deviceName);
					}
				}
				if (!BasicUtils.isEmpty(obj[4])) {
					String brandName = obj[4].toString();
					if (!BasicUtils.isEmpty(brandName)) {
						dev.setBrandName(brandName);
					}
				}
				if (!BasicUtils.isEmpty(obj[5])) {
					String deviceType = obj[5].toString();
					if (!BasicUtils.isEmpty(deviceType)) {
						dev.setDeviceType(deviceType);
					}
				}
				if (!BasicUtils.isEmpty(obj[6])) {
					String boardname = obj[6].toString();
					if (!BasicUtils.isEmpty(boardname)) {
						dev.setBoardname(boardname);
					}
				}
				if (!BasicUtils.isEmpty(obj[7])) {
					String usbport = obj[7].toString();
					if (!BasicUtils.isEmpty(usbport)) {
						dev.setUsbport(usbport);
					}
				}
				if (!BasicUtils.isEmpty(obj[8])) {
					String baudrate = obj[8].toString();
					if (!BasicUtils.isEmpty(baudrate)) {
						dev.setBaudrate(baudrate);
					}
				}
				if (!BasicUtils.isEmpty(obj[9])) {
					String parity = obj[9].toString();
					if (!BasicUtils.isEmpty(parity)) {
						dev.setParity(parity);
					}
				}
				if (!BasicUtils.isEmpty(obj[10])) {
					String databits = obj[10].toString();
					if (!BasicUtils.isEmpty(databits)) {
						dev.setDatabits(databits);
					}
				}
				if (!BasicUtils.isEmpty(obj[11])) {
					String stopbits = obj[11].toString();
					if (!BasicUtils.isEmpty(stopbits)) {
						dev.setStopbits(stopbits);
					}
				}
				registeredDevices.add(dev);
			}
		}

		json.setRegisteredDevices(registeredDevices);

		// setting up dropdowns
		RegisterDeviceDropdownJSon dropdown = new RegisterDeviceDropdownJSon();
		HashMap<String, List<String>> deviceBrandName = new HashMap<>();
		String fetchdevTypeBrand = "SELECT obj FROM InicuDevice as obj";
		List<InicuDevice> result = patientDao.getListFromMappedObjNativeQuery(fetchdevTypeBrand);
		if (!BasicUtils.isEmpty(result)) {
			for (InicuDevice dev : result) {
				if (!BasicUtils.isEmpty(deviceBrandName.get(dev.getBrandname()))) {
					List<String> listDev = deviceBrandName.get(dev.getBrandname());
					listDev.add(dev.getDevicename());
					deviceBrandName.put(dev.getBrandname(), listDev);
				} else {
					List<String> listDevices = new ArrayList<>();
					listDevices.add(dev.getDevicename());
					deviceBrandName.put(dev.getBrandname(), listDevices);
				}
			}
		}

		dropdown.setDeviceBrandName(deviceBrandName);

		HashMap<String, List<String>> deviceTypeBrand = new HashMap<>();
		String fetchDevRef = "select devtype.device_type, " + "dev.brandname " + "from ref_device_type as devtype "
				+ "left outer join " + "ref_inicu_devices as dev  " + "on (devtype.devicetypeid = dev.devicetypeid)";
		List<Object[]> resultDevRef = deviceDAO.executeNativeQuery(fetchDevRef);
		if (!BasicUtils.isEmpty(resultDevRef)) {
			for (Object[] obj : resultDevRef) {
				if (!BasicUtils.isEmpty(deviceTypeBrand.get(obj[0].toString()))) {
					List<String> listDev = deviceTypeBrand.get(obj[0].toString());
					if (!BasicUtils.isEmpty(obj[1])) {
						if (!listDev.contains(obj[1].toString())) {
							listDev.add(obj[1].toString());
						}
					}
					deviceTypeBrand.put(obj[0].toString(), listDev);
				} else {
					List<String> listDevices = new ArrayList<>();
					if (!BasicUtils.isEmpty(obj[1])) {
						listDevices.add(obj[1].toString());
					}
					deviceTypeBrand.put(obj[0].toString(), listDevices);
				}
			}
		}
		dropdown.setDeviceTypeBrand(deviceTypeBrand);

		List<String> deviceCommunicationType = new ArrayList<>();
		String queryToFetchCommunicationType = "SELECT obj FROM InicuDevice as obj";
		List<InicuDevice> communicationTypeList = patientDao.getListFromMappedObjNativeQuery(queryToFetchCommunicationType);
		if (!BasicUtils.isEmpty(communicationTypeList)) {
			for (InicuDevice device : communicationTypeList) {
				if (!BasicUtils.isEmpty(device.getCommunicationtype())
						&& !deviceCommunicationType.contains(device.getCommunicationtype())) {
					deviceCommunicationType.add(device.getCommunicationtype());
				}
			}
		}
		dropdown.setDeviceCommunicationType(deviceCommunicationType);

		List<String> deviceUsbPort = new ArrayList<>();
		deviceUsbPort.add("/dev/ttyS0");
		deviceUsbPort.add("/dev/ttyS1");
		deviceUsbPort.add("/dev/ttyS2");
		deviceUsbPort.add("/dev/ttyS3");
		deviceUsbPort.add("/dev/ttyS4");
		deviceUsbPort.add("/dev/ttyUSB0");
		deviceUsbPort.add("/dev/ttyUSB1");
		dropdown.setDeviceUsbPort(deviceUsbPort);

		List<String> deviceBaudRate = new ArrayList<>();
		deviceBaudRate.add("300");
		deviceBaudRate.add("600");
		deviceBaudRate.add("1200");
		deviceBaudRate.add("2400");
		deviceBaudRate.add("4800");
		deviceBaudRate.add("9600");
		deviceBaudRate.add("19200");
		deviceBaudRate.add("38400");
		deviceBaudRate.add("57600");
		deviceBaudRate.add("115200");
		deviceBaudRate.add("230400");
		deviceBaudRate.add("460800");
		deviceBaudRate.add("576000");
		deviceBaudRate.add("927600");
		deviceBaudRate.add("1000000");
		deviceBaudRate.add("2000000");
		dropdown.setDeviceBaudRate(deviceBaudRate);

		List<String> deviceParity = new ArrayList<>();
		deviceParity.add("None");
		deviceParity.add("Odd");
		deviceParity.add("Even");
//		deviceParity.add("Mark");
//		deviceParity.add("Space");
		dropdown.setDeviceParity(deviceParity);


		List<String> deviceDataBits = new ArrayList<>();
		deviceDataBits.add("5");
		deviceDataBits.add("6");
		deviceDataBits.add("7");
		deviceDataBits.add("8");
		dropdown.setDeviceDataBits(deviceDataBits);

		List<String> deviceStopBits = new ArrayList<>();
		deviceStopBits.add("1");
		deviceStopBits.add("2");
		dropdown.setDeviceStopBits(deviceStopBits);

		json.setDropDowns(dropdown);
		RegisteredDevicesJson device = new RegisteredDevicesJson();
		json.setDeviceDetails(device);
		return json;
	}

//	private static final SecureRandom secureRandom = new SecureRandom(); // threadsafe
//	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); // threadsafe
//
//	public JSONObject getTokenForichr(String uhid, String deviceId, String message) {
//
//		IchrTokenForVideoDetail ichrTokenForVideoDetail = new IchrTokenForVideoDetail();
//		JSONObject json = new JSONObject();
//		String token = "";
//		if (message.equals("icHrUsEr")) {
//			byte[] randomBytes = new byte[24];
//			secureRandom.nextBytes(randomBytes);
//			token = base64Encoder.encodeToString(randomBytes);
//			try {
//				json.put("token", token);
//				ichrTokenForVideoDetail.setUhid(uhid);
//				ichrTokenForVideoDetail.setToken(token);
//				
//				Timestamp tokenEndTime = new Timestamp(System.currentTimeMillis());
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(tokenEndTime);
//				cal.add(Calendar.DAY_OF_WEEK, 1);
//				
//				tokenEndTime.setTime(cal.getTime().getTime()); // or
//				tokenEndTime = new Timestamp(cal.getTime().getTime());
//				ichrTokenForVideoDetail.setTokenEndTime(tokenEndTime);
//
//				patientDao.saveObject(ichrTokenForVideoDetail);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//		return json;
//	}

	@Override
	public String getLiveVideoCredentialForichr(String uhid) {
		List<String> cameraFeedList = new ArrayList<>();
        BabyDetail babyDetail = null;
		if (!BasicUtils.isEmpty(uhid)) {
			String query = "select obj from BabyDetail as obj where uhid='" + uhid + "'";
			List<BabyDetail> babyData = patientDao.getListFromMappedObjNativeQuery(query);
			if (!BasicUtils.isEmpty(babyData)) {
                babyDetail = babyData.get(0);
				String branchName = babyData.get(0).getBranchname();

				String hospitalBranchId = "";
				query = "select obj from RefHospitalbranchname as obj where branchname='" + branchName + "'";
				List<RefHospitalbranchname> hospitalDetails = patientDao.getListFromMappedObjNativeQuery(query);
				if (!BasicUtils.isEmpty(hospitalDetails)) {
					hospitalBranchId = hospitalDetails.get(0).getHospitalbranchid();
				}
				String[] hospitalNames = branchName.split(",");

				String fetchDeviceNameDetails = "SELECT upper(b.tinyboxname),a.uhid,a.deviceid FROM bed_device_detail a " +
						"INNER JOIN ref_inicu_bbox as b " +
						"ON a.bbox_device_id=b.bbox_id " +
						"WHERE a.uhid = '" + uhid + "' and a.status = 'true' and (b.bboxname is null or b.bboxname = '')";
				List<Object[]> deviceBoxName = inicuDao.getListFromNativeQuery(fetchDeviceNameDetails);
				if (!BasicUtils.isEmpty(deviceBoxName)) {
					Object[] object = deviceBoxName.get(0);
					//String boxName = object[0] != null ? object[0].toString() : "";
					String tinyboxName = object[0] != null ? object[0].toString() : "";
					String deviceName = "";
					if (!BasicUtils.isEmpty(tinyboxName)) {
						deviceName = tinyboxName.replace("-", "");
						deviceName = deviceName.replace(" ", "");
					}

                    deviceName = deviceName + ".stream_720p";
					cameraFeedList.add(BasicConstants.props.getProperty(BasicConstants.PROP_WOWZA_SDP_URL));
					cameraFeedList.add(BasicConstants.SCHEMA_NAME + "_" + hospitalBranchId);
					cameraFeedList.add(deviceName);

				}
			}
		}

		String SDP_url = "";
		String videoURL = "https://";
		if (!BasicUtils.isEmpty(cameraFeedList)) {

			if (!BasicUtils.isEmpty(cameraFeedList.get(0))) {
				SDP_url = cameraFeedList.get(0).split("/")[2];

				videoURL += SDP_url;
			}
			if (!BasicUtils.isEmpty(cameraFeedList.get(1))) {
				String appName = cameraFeedList.get(1);
				videoURL += "/" + appName;

			}
			if (!BasicUtils.isEmpty(cameraFeedList.get(2))) {
				String streamName = cameraFeedList.get(2).split("_")[0];
                if(!BasicUtils.isEmpty(babyDetail.getAudioEnabled()) && babyDetail.getAudioEnabled()) {
                    streamName = cameraFeedList.get(2).split("_")[0];
                }else{
                    streamName = cameraFeedList.get(2).split("_")[0] + "_videoOnly";
                }
				videoURL += "/" + streamName;
			}
			videoURL += "/playlist.m3u8";
			System.out.println(videoURL);
		} else {
			return null;
		}

		return videoURL;
	}

	@Override
	public RegisterDeviceMasterJson registerDevice(RegisterDeviceMasterJson response) {
		RegisteredDevicesJson devDetails = response.getDeviceDetails();
		RegisterDeviceMasterJson json = new RegisterDeviceMasterJson();
		/*
		 * ResponseMessageObject res = new ResponseMessageObject();
		 *
		 * if (!BasicUtils.isEmpty(devDetails)) { boolean macStatus =
		 * macidExists(devDetails); if (macStatus) {
		 * res.setMessage("Mac id is already registered with another device.");
		 * res.setType(BasicConstants.MESSAGE_FAILURE); } else { // fetch inicudeviceid.
		 * boolean devDetailSaved = savedevDetailsObject(devDetails); if
		 * (devDetailSaved) {
		 *
		 * res.setMessage("Device registered successfully.");
		 * res.setType(BasicConstants.MESSAGE_SUCCESS); } else {
		 * res.setType(BasicConstants.MESSAGE_FAILURE); }
		 *
		 * } } // fetch all the registered devices. json = getRegisteredDevice();
		 * json.setResponse(res);
		 *
		 */
		return json;
	}

	@Override
	public boolean savedevDetailsObject(RegisteredDevicesJson devDetails) {
		String fetchRefDeviceId = "SELECT obj FROM InicuDevice as obj" + " WHERE brandname='"
				+ devDetails.getBrandName() + "' AND " + "devicename='" + devDetails.getDeviceName() + "'";

		List<InicuDevice> resultRefDevId = patientDao.getListFromMappedObjNativeQuery(fetchRefDeviceId);
		if (!BasicUtils.isEmpty(resultRefDevId)) {

			InicuDevice inicuDevice = resultRefDevId.get(0);
			DeviceDetail dev = new DeviceDetail();
			dev.setDevicetype(devDetails.getDeviceType());
			dev.setDevicebrand(devDetails.getBrandName());
			dev.setDeviceserialno(devDetails.getMacid());
			dev.setAvailable(true);
			dev.setDevicename(devDetails.getDeviceName());
			dev.setInicu_deviceid(inicuDevice.getDeviceid().longValue());
			try {
				patientDao.saveObject(dev);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else
			return false;

	}

	@Override
	public boolean macidExists(String macid) {
		if (!BasicUtils.isEmpty(macid)) {
			// check if macid is already present or not.
			String query = "SELECT obj FROM DeviceDetail as obj WHERE ipaddress='" + macid.trim() + "'";
			List<DeviceDetail> result = patientDao.getListFromMappedObjNativeQuery(query);
			if (!BasicUtils.isEmpty(result)) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	@Override
	public boolean macidExistsInMacIDTable(String macid) {
		if (!BasicUtils.isEmpty(macid)) {
			// check if macid is already present or not.
			String query = "SELECT obj FROM MacID as obj WHERE macid='" + macid.trim() + "'";
			List<DeviceDetail> result = patientDao.getListFromMappedObjNativeQuery(query);
			if (!BasicUtils.isEmpty(result)) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	@Override
	public ResponseMessageObject addBoard(String boardName) {
		// TODO Auto-generated method stub
		ResponseMessageObject resp = new ResponseMessageObject();
		try {

			String fetchboard = "SELECT ref.bbox_id FROM ref_inicu_bbox as ref WHERE ref.bboxname='" + boardName.trim()
					+ "'";
			List<Object> resultSet = deviceDAO.executeNativeQuery(fetchboard);
			if (!BasicUtils.isEmpty(resultSet)) {
				resp.setMessage("Board Name Already Exists.");
				resp.setType(BasicConstants.MESSAGE_FAILURE);
			} else {

				String query = "SELECT MAX(bbox_id)+1 FROM ref_inicu_bbox";
				List<Object> result = deviceDAO.executeNativeQuery(query);
				String id = null;
				if (!BasicUtils.isEmpty(result)) {
					id = result.get(0).toString().trim();
				} else {
					id = "1";
				}
				RefInicuBbox bbox = new RefInicuBbox();
				bbox.setActive(true);
				bbox.setBboxname(boardName + id);
				bbox.setBboxId(Long.parseLong(id));
				patientDao.saveObject(bbox);

				resp.setMessage("box added successfully.");
				resp.setType(BasicConstants.MESSAGE_SUCCESS);
			}

		} catch (Exception e) {
			e.printStackTrace();
			resp.setMessage(e.getMessage());
			resp.setType(BasicConstants.MESSAGE_FAILURE);
		}
		return resp;
	}

	@Override
	public InicuDeviceMastJson getInicuDeviceDetails() {
		InicuDeviceMastJson json = new InicuDeviceMastJson();
		List<InicuBoxJson> listOfBoxes = new ArrayList<>();
		String query = "SELECT obj FROM RefInicuBbox as obj WHERE active ='true' order by creationtime desc";
		List<RefInicuBbox> resultSet = patientDao.getListFromMappedObjNativeQuery(query);
		if (!BasicUtils.isEmpty(resultSet)) {
			for (RefInicuBbox bbox : resultSet) {
				InicuBoxJson boxJson = new InicuBoxJson();
				boxJson.setBoxName(bbox.getBboxname());

				// fetch list of boards associated with every box.
				String fetchBoard = "SELECT obj FROM RefInicuBboxBoard as obj WHERE bboxid = '" + bbox.getBboxId()
						+ "'";
				List<RefInicuBboxBoard> result = patientDao.getListFromMappedObjNativeQuery(fetchBoard);
				if (!BasicUtils.isEmpty(result)) {
					List<BoardDetailJson> listBoardDetails = new ArrayList<>();
					for (RefInicuBboxBoard board : result) {
						BoardDetailJson boardJson = new BoardDetailJson();
						boardJson.setBoardName(board.getBboardName());
						boardJson.setMacId(board.getBboardMacid());
						listBoardDetails.add(boardJson);
					}
					boxJson.setListBoardDetails(listBoardDetails);
				}
				listOfBoxes.add(boxJson);
			}
		}

		json.setListOfBoxes(listOfBoxes);
		return json;
	}

	@Override
	public ResponseMessageObject addBeagleBoneDevices(String boardName, String boxName, String macid) {
		ResponseMessageObject resp = new ResponseMessageObject();
		try {
			String fetchBoxid = "SELECT obj FROM RefInicuBbox as obj WHERE bboxname ='" + boxName
					+ "' and active='true'";
			List<RefInicuBbox> resultSet = patientDao.getListFromMappedObjNativeQuery(fetchBoxid);
			if (!BasicUtils.isEmpty(resultSet)) {
				// check if mac id is unique or not
				String macidQuery = "SELECT bboard_macid FROM ref_inicu_bbox_boards WHERE bboard_macid = '" + macid
						+ "'";
				List<Object> macResultSet = deviceDAO.executeNativeQuery(macidQuery);
				if (BasicUtils.isEmpty(macResultSet)) {
					String query = "SELECT MAX(board_id)+1 FROM  ref_inicu_bbox_boards as obj";
					List<Object> idlist = deviceDAO.executeNativeQuery(query);
					Long boardId = null;
					if (!BasicUtils.isEmpty(idlist)) {
						boardId = Long.parseLong(idlist.get(0).toString());
					} else {
						boardId = Long.parseLong("1");
					}

					Long boxid = resultSet.get(0).getBboxId();
					RefInicuBboxBoard board = new RefInicuBboxBoard();
					board.setActive(true);
					board.setBboardMacid(macid);
					board.setBboardName(boardName);
					board.setBboxid(boxid);
					board.setBoardId(boardId);
					patientDao.saveObject(board);

					resp.setMessage("board added successfully");
					resp.setType(BasicConstants.MESSAGE_SUCCESS);
				} else {
					resp.setMessage("Please check. Mac id is already assigned.");
					resp.setType(BasicConstants.MESSAGE_FAILURE);
				}
			} else {
				resp.setMessage("Oops could not get the Box id.");
				resp.setType(BasicConstants.MESSAGE_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setMessage(e.getMessage());
			resp.setType(BasicConstants.MESSAGE_FAILURE);
		}
		return resp;
	}

	@Override
	public ResponseMessageObject deleteBoard(String macid) {
		ResponseMessageObject resp = new ResponseMessageObject();
		try {
			String query = "delete from ref_inicu_bbox_boards where bboard_macid = '" + macid + "'";
			inicuDao.updateOrDeleteNativeQuery(query);
			resp.setMessage("board deleted successfully.");
			resp.setType(BasicConstants.MESSAGE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setMessage(e.getMessage());
			resp.setType(BasicConstants.MESSAGE_FAILURE);
		}
		return resp;
	}

	@Override
	public void saveDataToCassandra(Date date, String data, String boxid) {
		String uhid_ = "123456";
		if (!BasicUtils.isEmpty(uhid_)) {
			// String uhid = uhid_.get(0).toString();
			inifinityPool.execute(new InsertDataThread(date, data, uhid_));
		}
	}

	public String startBabyVideoRecording(String uhid,List<String> framesList){
		try {
			String applicationName = framesList.get(1);
			String deviceName = framesList.get(3);

			String streamName = deviceName + ".stream";
			String baseFile = streamName + ".mp4";
//			String deviceShortName = "NT" + deviceName.substring(7, 10);
			String deviceShortName = deviceName;

			String fileTemplate = uhid + "_" + deviceShortName + "_${RecordingStartTime}_${SegmentNumber}";
			String outputPath = "/wowzaRecords/Wowza/" + applicationName;

			StringBuilder startRecordingAPI = new StringBuilder();
			startRecordingAPI.append("/v2/servers/_defaultServer_/vhosts/_defaultVHost_/applications/");
			startRecordingAPI.append(applicationName);
			startRecordingAPI.append("/instances/_definst_/streamrecorders/");
			startRecordingAPI.append(streamName);

			// now start the recording
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("instanceName", "");
			jsonObject.put("fileVersionDelegateName", "");
			jsonObject.put("recorderName", streamName);
			jsonObject.put("currentSize", 0);
			jsonObject.put("segmentSchedule", "0 * * * * *");
			jsonObject.put("startOnKeyFrame", true);
			jsonObject.put("outputPath", outputPath);
			jsonObject.put("currentFile", "");
			jsonObject.put("saveFieldList", new JSONArray());
			jsonObject.put("recordData", true);

			jsonObject.put("applicationName", "");
			jsonObject.put("moveFirstVideoFrameToZero", false);
			jsonObject.put("recorderErrorString", "");
			jsonObject.put("segmentSize", 0);
			jsonObject.put("defaultRecorder", false);
			jsonObject.put("splitOnTcDiscontinuity", false);

			jsonObject.put("version", "com.wowza.wms.livestreamrecord.manager.StreamRecorderFileVersionDelegate");
			jsonObject.put("baseFile", baseFile);
			jsonObject.put("segmentDuration", 0);
			jsonObject.put("recordingStartTime", "");
			jsonObject.put("fileTemplate", fileTemplate);
			jsonObject.put("backBufferTime", 0);

			jsonObject.put("segmentationType", "SegmentBySchedule");
			jsonObject.put("currentDuration", 0);
			jsonObject.put("fileFormat", "MP4");
			jsonObject.put("recorderState", "");
			jsonObject.put("option", "Version existing file");

			String resposnseReceived = SimpleHttpClient.executeHttpPostWithJsonObject(startRecordingAPI.toString(), jsonObject);

			System.out.println("<----------- Start Recording Message Starts------------>");

			System.out.println("Response Received: "+resposnseReceived);

			System.out.println("Stream Name :"+streamName);
			System.out.println("Device Short Name :"+deviceShortName);

			System.out.println("File Template :"+fileTemplate);

			System.out.println("<----------- Start Recording Message End ------------>");

			JsonParser parser2 = new JsonParser();
			JsonObject jsonObj2 = (JsonObject) parser2.parse(resposnseReceived);

			System.out.println(jsonObj2.get("success"));
			System.out.println(jsonObj2.get("message"));

			return jsonObj2.get("message").toString();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String startVideoRecording(String uhid) {

		String result = null;
		boolean matchFound = false;
		List<String> framesList = getCameraFeed(uhid);
		if(framesList != null && framesList.size()>0){


			String applicationName  = framesList.get(1);
			String deviceName  = framesList.get(3);
			String streamName = deviceName + ".stream";
			String deviceShortName = "NT" + deviceName.substring(7,10);

			StringBuilder checkRecordingAPI = new StringBuilder();
			checkRecordingAPI.append("/v2/servers/_defaultServer_/vhosts/_defaultVHost_/applications/");
			checkRecordingAPI.append(applicationName);
			checkRecordingAPI.append("/instances/_definst_/streamrecorders");

			// now make the http request to check the running status
			try {
				String resultReturn =  SimpleHttpClient.executeWowzaHttpGet(checkRecordingAPI.toString());
				JsonParser parser = new JsonParser();
				JsonObject jsonObj = (JsonObject) parser.parse(resultReturn);

				// Check the response status then proceed accordingly
				if(!BasicUtils.isEmpty(jsonObj.get("streamrecorder"))){
					JsonArray streamRecorderList = (JsonArray) jsonObj.get("streamrecorder");

					if(streamRecorderList.size() >0 ){

						// now for the current device recording running status
						for (JsonElement streamElement :streamRecorderList) {
							JsonObject streamObject = streamElement.getAsJsonObject();
							if(!BasicUtils.isEmpty(streamObject.get("recorderState")) && streamObject.get("recorderName").toString().equalsIgnoreCase(streamName) &&
									streamObject.get("recorderState").toString().equalsIgnoreCase("Recording in Progress")){

									System.out.println("<----------- Recording in Progress Found ------------>");
								 	matchFound = true;
								 	// stream is running stop it
									String status = stopVideoRecording(uhid);
									if(status!=null){
										result = startBabyVideoRecording(uhid,framesList);
									}

							}else if (!BasicUtils.isEmpty(streamObject.get("recorderState")) && streamObject.get("recorderName").toString().equalsIgnoreCase(streamName) &&
									streamObject.get("recorderState").toString().equalsIgnoreCase("Waiting for stream")){
										String status = stopVideoRecording(uhid);
										System.out.println("Waiting for stream or stream not available");
							} else if (!BasicUtils.isEmpty(streamObject.get("recorderState")) && streamObject.get("recorderName").toString().equalsIgnoreCase(streamName) &&
									streamObject.get("recorderState").toString().equalsIgnoreCase("Recorder error")){
									System.out.println(streamObject.get("recorderErrorString").toString());
							}
						}
					}
				}else{

					String responseStatus = jsonObj.get("success").toString();
					String reponseCode = jsonObj.get("code").toString();

					String responseMessage = jsonObj.get("message").toString();
					System.out.println("Message Received From the Server :"+responseMessage);
				}

				if(matchFound == false){
					// No Recording is running currently
					result = startBabyVideoRecording(uhid,framesList);
				}

			}catch (Exception e){
				e.printStackTrace();;
			}
		}
		return result;
	}

	@Override
	public String stopVideoRecording(String uhid) {
		String result = null;
		List<String> framesList = getCameraFeed(uhid);
		if(framesList != null && framesList.size()>0){

			String applicationName  = framesList.get(1);
			String deviceName  = framesList.get(3);

			String streamName = deviceName + ".stream";
			String deviceShortName = "NT" + deviceName.substring(7,10);

			StringBuilder stopRecordingAPI = new StringBuilder();
			stopRecordingAPI.append("/v2/servers/_defaultServer_/vhosts/_defaultVHost_/applications/");
			stopRecordingAPI.append(applicationName);
			stopRecordingAPI.append("/instances/_definst_/streamrecorders/");
			stopRecordingAPI.append(streamName);
			stopRecordingAPI.append("/actions/stopRecording");

			// now make the http request to check the running status
			try {
				String response =  SimpleHttpClient.executeHttpPutRequest(stopRecordingAPI.toString());
				JsonParser parser2 = new JsonParser();
				JsonObject jsonObj2 = (JsonObject) parser2.parse(response);

				System.out.println("<----------- Stop Recording Message Starts------------>");
				System.out.println(response);
				System.out.println("<----------- Stop Recording Message End ------------>");

				// handle the json Object
				result = jsonObj2.get("message").toString();
			}catch (Exception e){
				e.printStackTrace();;
			}
		}
		return result;
	}

	@Override
	public ResponseMessageObject mapInicuDeviceWithPatient(AddDeviceTemplateJson deviceData, String entryDate) {
		ResponseMessageObject rObj = new ResponseMessageObject();
		try {
			// check if the device is already connected
			String query = "SELECT obj FROM BedDeviceDetail as obj " + "WHERE status ='true' AND time_to is null "
					+ "AND bbox_device_id='" + deviceData.getDeviceName() + "'";

			List<BedDeviceDetail> resultSet = patientDao.getListFromMappedObjNativeQuery(query);
			if (!BasicUtils.isEmpty(resultSet) && resultSet.size() > 0) {
				rObj.setMessage("Device is already Mapped to another bed.");
				rObj.setType(BasicConstants.MESSAGE_SUCCESS);
			} else {

				if (!BasicUtils.isEmpty(deviceData)) {
					BedDeviceDetail beddevDetail = new BedDeviceDetail();
					beddevDetail.setBbox_device_id(Long.valueOf(deviceData.getDeviceName()));
					beddevDetail.setBedid(deviceData.getBedId());
					beddevDetail.setStatus(true);
					beddevDetail.setUhid(deviceData.getUhid().trim());
					beddevDetail.setDeviceid(deviceData.getDeviceName().toString());
					if (!BasicUtils.isEmpty(entryDate)) {
						try {
							SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z");
							Date parsedDate = dateFormat.parse(entryDate);
							//beddevDetail.setTime_to(new Timestamp(parsedDate.getTime()));
							beddevDetail.setTime_from(new Timestamp(parsedDate.getTime()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					patientDao.saveObject(beddevDetail);

					// get the device type with Bbox_device_id
					String fetchNeoBoxDetails = "SELECT tinyboxname FROM ref_inicu_bbox WHERE bbox_id = '" + deviceData.getDeviceName().toString() + "' and active = '" + true + "'";
					Object boxname = inicuDao.getListFromNativeQuery(fetchNeoBoxDetails);

					if(!BasicUtils.isEmpty(boxname) && !BasicUtils.isEmpty(BasicConstants.RECORDING_ENABLED) && BasicConstants.RECORDING_ENABLED == true){
						// start the recording if the connecting device is camera
						startVideoRecording(deviceData.getUhid());
					}else{
						System.out.println("Connected Device is not Camera");
					}

				} else {
					rObj.setMessage("Oops! could not be saved.Date received is null");
					rObj.setType(BasicConstants.MESSAGE_FAILURE);
				}

				rObj.setMessage("Device Added Successfully.");
				rObj.setType(BasicConstants.MESSAGE_SUCCESS);
			}

		} catch (Exception e) {
			rObj.setMessage(e.getMessage());
			rObj.setType(BasicConstants.MESSAGE_FAILURE);
		}
		return rObj;
	}

	@Override
	public ResponseMessageObject disconnectInicuDevices(AddDeviceTemplateJson device, String entryDate) {
		ResponseMessageObject obj = new ResponseMessageObject();
		// disconnect inicu medical devices.
		try {
			String query = "SELECT obj FROM BedDeviceDetail as obj WHERE uhid='" + device.getUhid() + "' "
					+ "AND status = 'true' AND bbox_device_id = '" + device.getDeviceName()
					+ "' order by creationtime desc";

			List<BedDeviceDetail> resultSet = patientDao.getListFromMappedObjNativeQuery(query);
			if (!BasicUtils.isEmpty(resultSet)) {

				// get the device type with Bbox_device_id
				String fetchNeoBoxDetails = "SELECT tinyboxname FROM ref_inicu_bbox WHERE bbox_id = '" + device.getDeviceName().toString() + "' and active = '" + true + "'";
				Object boxname = inicuDao.getListFromNativeQuery(fetchNeoBoxDetails);

				if(!BasicUtils.isEmpty(boxname) && !BasicUtils.isEmpty(BasicConstants.RECORDING_ENABLED) && BasicConstants.RECORDING_ENABLED == true){
					// now stop the recording
					String response = stopVideoRecording(device.getUhid().toString());
				}else{
					System.out.println("Disconncting Device is not Camera");
				}


				BedDeviceDetail dev = resultSet.get(0);
				dev.setStatus(false);
				try {
					//dev.setTime_to(new Timestamp(new Date().getTime()));
					SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z");
					Date parsedDate = dateFormat.parse(entryDate);
					dev.setTime_to(new Timestamp(parsedDate.getTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				patientDao.saveObject(dev);

				// Check if the disconnected device is Camera then stop the job Running on ICHR
//				String checkCameraConnection = "SELECT obj FROM RefInicuBbox as obj WHERE bbox_id='" + device.getDeviceName() + "' "
//						+ "AND tinyboxname like 'CAM-%' AND bboxname is null";
//
//				List<RefInicuBbox> cameraConnectionList = patientDao.getListFromMappedObjNativeQuery(checkCameraConnection);
//
//				if (!BasicUtils.isEmpty(cameraConnectionList) && cameraConnectionList.size() > 0) {
//					// Kindly stop the job running corresponds to this baby
//					String res = deleteJobByUhid(device.getUhid(), "simple");
//					System.out.println(res);
//				}

			}
			obj.setMessage("Device Disconnected successfully.");
			obj.setType(BasicConstants.MESSAGE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			obj.setMessage(e.getMessage());
			obj.setType(BasicConstants.MESSAGE_FAILURE);
		}

		return obj;
	}

	public static String deleteJobByUhid(String uhid, String type) {
		String response = "Deleted";
		try {
			String companyId = BasicConstants.ICHR_SCHEMA;
			;
			String requestType = "deleteJobWithUhid";
			// now send the notification

			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("companyId", companyId));
			postParameters.add(new BasicNameValuePair("uid", uhid));
			postParameters.add(new BasicNameValuePair("requestType", requestType));
			postParameters.add(new BasicNameValuePair("jobName", type));
			postParameters.add(new BasicNameValuePair("message", null));
			postParameters.add(new BasicNameValuePair("smsMessage", null));
			postParameters.add(new BasicNameValuePair("smsReminderMsg", null));
			postParameters.add(new BasicNameValuePair("morningFromTime", null));
			postParameters.add(new BasicNameValuePair("morningToTime", null));
			postParameters.add(new BasicNameValuePair("eveningFromTime", null));
			postParameters.add(new BasicNameValuePair("eveningToTime", null));
			response = SimpleHttpClient.executeHttpPost(BasicConstants.PUSH_NOTIFICATION, postParameters);
			// now try making the post request using the ICHR android post method

			System.out.println("Notification Sent and response received is :" + response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}


	private Float calculateMeanFromList(List<Float> listVal) {
		Float value = null;
		if (!BasicUtils.isEmpty(listVal)) {
			Float total = Float.parseFloat("0");
			for (Float val : listVal) {
				total = total + val;
			}

			value = total / listVal.size();
		}
		return value;
	}

	@Override
	public void saveMultipleDataReceivedFromBox(JSONArray jsonArray) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonData = (JSONObject) jsonArray.getJSONObject(i);
				System.out.println("JSON Data To Be Saved = " + jsonData.toString());
				saveDataReceivedFromBox(jsonData);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveDataReceivedFromBox(JSONObject jsonData) {
		// TODO Auto-generated method stub
		try {
			if (jsonData.has("box_name")) {
				if (jsonData.has("mac_id")) {
					String macId = jsonData.getString("mac_id");
					String queryMacId = "select obj from MacID obj where macid='" + macId + "'";
					List<MacID> listMacId = patientDao.getListFromMappedObjNativeQuery(queryMacId);
					if (BasicUtils.isEmpty(listMacId)) {
						MacID newMacId = new MacID();
						newMacId.setMacid(macId);
						newMacId.setAvailable(true);
						try {
							patientDao.saveObject(newMacId);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				String boxName = jsonData.getString("box_name");
				boxName = "neo-" + boxName.substring(3, boxName.length());

				System.out.println("heartbeat found for " + jsonData.getString("box_name"));
				// first save data in deviceheartbeat table
				DeviceHeartbeat heartbeat = new DeviceHeartbeat();
				heartbeat.setdatamessage(jsonData.getString("data"));
				heartbeat.setDeviceserialno(boxName);
				try {
					patientDao.saveObject(heartbeat);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

//				//Check Whether the box is added or not
//				String branchName = jsonData.getString("branch_name");
//				String queryToFetchBBox = "SELECT obj FROM RefInicuBbox as obj WHERE bboxname ='" + boxName + "'";
//				List<RefInicuBbox> fetchBoxIdList = patientDao.getListFromMappedObjNativeQuery(queryToFetchBBox);
//				RefInicuBbox BBox = new RefInicuBbox();
//				if (!BasicUtils.isEmpty(fetchBoxIdList)) {
//					BBox = fetchBoxIdList.get(0);
//				}
//				else {
//					String queryToFetchNewBBoxId = "SELECT MAX(bboxId)+1 FROM RefInicuBbox";
//					List<Long> fetchNewBoxIdList = patientDao.getListFromMappedObjNativeQuery(queryToFetchNewBBoxId);
//					String id = null;
//					if (!BasicUtils.isEmpty(fetchNewBoxIdList)) {
//						id = fetchNewBoxIdList.get(0).toString().trim();
//					}
//					else {
//						id = "1";
//					}
//
//					BBox.setBboxId(Long.parseLong(id));
//					BBox.setBboxname(boxName);
//				}
//
//				BBox.setActive(true);
//				BBox.setBranchname(branchName);
//
//				try {
//					patientDao.saveObject(BBox);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				//Add box medical details
//				String boxSerialNo = jsonData.getString("box_name");
//				String description = jsonData.getString("board_name");
//				String deviceType = jsonData.getString("device_type");
//				String deviceBrand = jsonData.getString("device_brand");
//				String deviceName = jsonData.getString("device_name");
//				String usbPort = jsonData.getString("usb_port");
//				String baudRate = jsonData.getString("baud_rate");
//				String parity = jsonData.getString("parity");
//				String dataBits = jsonData.getString("data_bits");
//				String stopBits = jsonData.getString("stop_bits");
//				String deviceMacId = jsonData.getString("mac_id");
//				Long inicuDeviceId = BBox.getBboxId();
//
//				String queryToFetchDeviceDetail = "SELECT obj FROM DeviceDetail as obj WHERE "
//						+ "deviceserialno='" + boxSerialNo + "'";
//				List<DeviceDetail> deviceDetailList = patientDao.getListFromMappedObjNativeQuery(queryToFetchDeviceDetail);
//				DeviceDetail deviceDetail = new DeviceDetail();
//				if (!BasicUtils.isEmpty(deviceDetailList)) {
//					deviceDetail = deviceDetailList.get(0);
//				}
//
//				deviceDetail.setAvailable(true);
//				deviceDetail.setDescription(description);
//				deviceDetail.setDevicebrand(deviceBrand);
//				deviceDetail.setDevicename(deviceName);
//				deviceDetail.setDeviceserialno(boxSerialNo);
//				deviceDetail.setDevicetype(deviceType);
//				deviceDetail.setIpaddress(deviceMacId);
//				deviceDetail.setInicu_deviceid(inicuDeviceId);
//				deviceDetail.setBranchname(branchName);
//
//				String queryToFetchInicuDevice = "SELECT * FROM ref_inicu_device as obj WHERE bboxname ='" + boxName + "'";
//				String queryToFetchCommTypeDetail = "select * from ref_inicu_devices "
//						+ "where devicename='" + deviceDetail.getDevicename() + "'";
//				List<Object[]> refInicuDevicesList = patientDao.getListFromNativeQuery(queryToFetchCommTypeDetail);
//				if(!BasicUtils.isEmpty(refInicuDevicesList)) {
//					String communicationType = ((refInicuDevicesList.get(0)[4]).toString());
//					if(communicationType.equalsIgnoreCase("LAN")) {
//						usbPort = null;
//						baudRate = null;
//						parity = null;
//						dataBits = null;
//						stopBits = null;
//					}
//				}
//
//				deviceDetail.setUsbport(usbPort);
//				deviceDetail.setBaudrate(baudRate);
//				deviceDetail.setParity(parity);
//				deviceDetail.setDatabits(dataBits);
//				deviceDetail.setStopbits(stopBits);
//
//				try {
//					patientDao.saveObject(deviceDetail);
//				}catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}


				if (!BasicUtils.isEmpty(boxName)) {
					// push data with relevant boxname.
					// find uhid.
					String query = "select uhid from bed_device_detail "
							+ "where bbox_device_id = (select bbox_id FROM ref_inicu_bbox WHERE bboxname = '" + boxName
							+ "') " + "AND status=true";
					List<Object> resultSet = patientDao.getListFromNativeQuery(query);
					if (!BasicUtils.isEmpty(resultSet)) {
						String uhid = resultSet.get(0).toString();
						if (!BasicUtils.isEmpty(uhid)) {
							// device time not coming from the box as of now.
							// Long longdate =
							// (Long)jsonData.get("device_time");
							Long longdate = new Date().getTime();
							String data = jsonData.getString("data");
							Date deviceDate = new Date(longdate);
							String serverDate = jsonData.getString("server_time");
							System.out.println("Server Date For Cassandra = " + serverDate);
							String deviceName = jsonData.getString("device_name");
							executor.execute(
									new InsertDataThread(deviceDate, serverDate, data, uhid, true, deviceName));
						}
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveDeviceIncubatorWarmerDetail(HashMap<String, Float> mappedMean, String uhid, String beddeviceid,
												Long deviceTime) {
		if (!BasicUtils.isEmpty(mappedMean) && !BasicUtils.isEmpty(uhid)) {
			DeviceIncubatorWarmerDetail devIncubator = new DeviceIncubatorWarmerDetail();
			DashboardDeviceIncubatorWarmerDetail devIncubator2 = new DashboardDeviceIncubatorWarmerDetail();
			if (!BasicUtils.isEmpty(beddeviceid)) {
				devIncubator.setBeddeviceid(beddeviceid);
				devIncubator2.setBeddeviceid(beddeviceid);
			}


			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_ambientTemperature)) {
				Float intValue = (mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_ambientTemperature));
				devIncubator.setAmbientTemperature(intValue.toString());
				devIncubator2.setAmbientTemperature(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_incubatorMode)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_incubatorMode));
				devIncubator.setIncubatorMode(intValue.toString());
				devIncubator2.setIncubatorMode(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_warmerMode)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_warmerMode));
				devIncubator.setWarmerMode(intValue.toString());
				devIncubator2.setWarmerMode(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_WarmerSetTemperature)) {
				Float intValue = (mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_WarmerSetTemperature));
				devIncubator.setWarmerSetTemp(intValue.toString());
				devIncubator2.setWarmerSetTemp(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_Air_Temp)) {
				Float intValue = (mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_Air_Temp));
				devIncubator.setAirTemp(intValue.toString());
				devIncubator2.setAirTemp(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_Set_Air_Temp)) {
				Float intValue = (mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_Set_Air_Temp));
				devIncubator.setSetAirTemp(intValue.toString());
				devIncubator2.setSetAirTemp(intValue.toString());
			}
			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_Weight)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_Weight));
				devIncubator.setBabyWeight(intValue.toString());
				devIncubator2.setBabyWeight(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_heaterPower)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_heaterPower));
				devIncubator.setHeaterPower(intValue.toString());
				devIncubator2.setHeaterPower(intValue.toString());
			}
			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_Skin_Temp1)) {
				Float intValue = (mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_Skin_Temp1));
				devIncubator.setSkinTemp1(intValue.toString());
				devIncubator2.setSkinTemp1(intValue.toString());
			}
			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_Skin_Temp2)) {
				Float intValue = (mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_Skin_Temp2));
				devIncubator.setSkinTemp2(intValue.toString());
				devIncubator2.setSkinTemp2(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_Humidity)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_Humidity));
				devIncubator.setHumidity(intValue.toString());
				devIncubator2.setHumidity(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_Set_Humidity)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_Set_Humidity));
				devIncubator.setSetHumidity(intValue.toString());
				devIncubator2.setSetHumidity(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_PATIENT_CONTROL_TEMP)) {
				Float intValue = (mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_PATIENT_CONTROL_TEMP));
				devIncubator.setPatientControlTemp(intValue.toString());
				devIncubator2.setPatientControlTemp(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_PATIENT_MODE)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_PATIENT_MODE));
				devIncubator.setPatientMode(intValue.toString());
				devIncubator2.setPatientMode(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_OPEN_BED_MODE)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_OPEN_BED_MODE));
				devIncubator.setOpenBedMode(intValue.toString());
				devIncubator2.setOpenBedMode(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_CLOSED_BED_MODE)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_CLOSED_BED_MODE));
				devIncubator.setClosedBedMode(intValue.toString());
				devIncubator2.setClosedBedMode(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_O2_SET_POINT)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_O2_SET_POINT));
				devIncubator.setO2SetPoint(intValue.toString());
				devIncubator2.setO2SetPoint(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_O2_MEASURMENT)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_O2_MEASURMENT));
				devIncubator.setO2_measurment(intValue.toString());
				devIncubator2.setO2_measurment(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_SPO2_MEASUREMENT)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_SPO2_MEASUREMENT));
				devIncubator.setSpo2Measurment(intValue.toString());
				devIncubator2.setSpo2Measurment(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_INCUBATOR_WARMER_PULSE_RATE_MEASUREMENT)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_INCUBATOR_WARMER_PULSE_RATE_MEASUREMENT));
				devIncubator.setPulseRateMeasurment(intValue.toString());
				devIncubator2.setPulseRateMeasurment(intValue.toString());
			}

			devIncubator.setUhid(uhid);
			devIncubator.setStarttime(new Timestamp(deviceTime));
			devIncubator2.setUhid(uhid);
			devIncubator2.setStarttime(new Timestamp(deviceTime));

			try {
				patientDao.saveObject(devIncubator);
				patientDao.saveObject(devIncubator2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void saveDeviceMonitorDetail(HashMap<String, Float> mappedMean, String uhid, String beddeviceid,
										Long deviceTime) {
		if (!BasicUtils.isEmpty(mappedMean) && !BasicUtils.isEmpty(uhid)) {
			DeviceMonitorDetail devMonitor = new DeviceMonitorDetail();
			DashboardDeviceMonitorDetail devMonitor2 = new DashboardDeviceMonitorDetail();
			if (!BasicUtils.isEmpty(beddeviceid)) {
				devMonitor.setBeddeviceid(beddeviceid);
				devMonitor2.setBeddeviceid(beddeviceid);
			}
			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_PA)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_PA));
				devMonitor.setPa(intValue.toString());
				devMonitor2.setPa(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_CO2_RESP_RATE)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_CO2_RESP_RATE));
				devMonitor.setCo2Resprate(intValue.toString());
				devMonitor2.setCo2Resprate(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_DIA_BP)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_DIA_BP));
				devMonitor.setDiaBp(intValue.toString());
				devMonitor2.setDiaBp(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_ECG_RESP_RATE)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_ECG_RESP_RATE));
				devMonitor.setEcgResprate(intValue.toString());
				devMonitor2.setEcgResprate(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_ETCO2)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_ETCO2));
				devMonitor.setEtco2(intValue.toString());
				devMonitor2.setEtco2(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_HEART_RATE)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_HEART_RATE));
				devMonitor.setHeartrate(intValue.toString());
				devMonitor2.setHeartrate(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_MEAN_BP)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_MEAN_BP));
				devMonitor.setMeanBp(intValue.toString());
				devMonitor2.setMeanBp(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_NIBP_D)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_NIBP_D));
				devMonitor.setNbp_d(intValue.toString());
				devMonitor2.setNbpD(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_NIBP_M)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_NIBP_M));
				devMonitor.setNbp_m(intValue.toString());
				devMonitor2.setNbpM(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_NIBP_S)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_NIBP_S));
				devMonitor.setNbp_s(intValue.toString());
				devMonitor2.setNbpS(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_IBP_D)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_IBP_D));
				devMonitor.setIbp_d(intValue.toString());
				devMonitor2.setIbpD(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_IBP_M)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_IBP_M));
				devMonitor.setIbp_m(intValue.toString());
				devMonitor2.setIbpM(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_IBP_S)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_IBP_S));
				devMonitor.setIbp_s(intValue.toString());
				devMonitor2.setIbpS(intValue.toString());
			}
			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_PULSE_RATE)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_PULSE_RATE));
				devMonitor.setPulserate(intValue.toString());
				devMonitor2.setPulserate(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_SPO2)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_SPO2));
				devMonitor.setSpo2(intValue.toString());
				devMonitor2.setSpo2(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_SYS_BP)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_SYS_BP));
				devMonitor.setSysBp(intValue.toString());
				devMonitor2.setSysBp(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_SO2_1)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_SO2_1));
				devMonitor.setO3rSO2_1(intValue.toString());
				devMonitor2.setO3rSO2_1(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_SO2_2)) {
				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_SO2_2));
				devMonitor.setO3rSO2_2(intValue.toString());
				devMonitor2.setO3rSO2_2(intValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_PI)) {
				Float intValue = (mappedMean.get(DeviceConstants.DEVICE_MONITOR_PI));
				devMonitor.setPi(intValue.toString());
				devMonitor2.setPi(intValue.toString());
			}

			devMonitor.setUhid(uhid);
			devMonitor2.setUhid(uhid);
			devMonitor.setStarttime(new Timestamp(deviceTime));
			devMonitor2.setStarttime(new Timestamp(deviceTime));

			try {
				patientDao.saveObject(devMonitor);
				patientDao.saveObject(devMonitor2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void saveDeviceCerebralOximeterDetail(HashMap<String, Float> mappedMean, String uhid, String beddeviceid,
												 Long deviceTime) {
		if (!BasicUtils.isEmpty(mappedMean) && !BasicUtils.isEmpty(uhid)) {
			DeviceCerebralOximeterDetail devCerebralOximeter = new DeviceCerebralOximeterDetail();
			if (!BasicUtils.isEmpty(beddeviceid)) {
				devCerebralOximeter.setBeddeviceid(beddeviceid);
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_CEREBRAL_OXIMETER_RSO2_1)) {
				Integer rso2IntValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_CEREBRAL_OXIMETER_RSO2_1));
				devCerebralOximeter.setChannel1_rso2(rso2IntValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_CEREBRAL_OXIMETER_RSO2_2)) {
				Integer rso2IntValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_CEREBRAL_OXIMETER_RSO2_2));
				devCerebralOximeter.setChannel2_rso2(rso2IntValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_CEREBRAL_OXIMETER_RSO2_3)) {
				Integer rso2IntValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_CEREBRAL_OXIMETER_RSO2_3));
				devCerebralOximeter.setChannel3_rso2(rso2IntValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_CEREBRAL_OXIMETER_RSO2_4)) {
				Integer rso2IntValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_CEREBRAL_OXIMETER_RSO2_4));
				devCerebralOximeter.setChannel4_rso2(rso2IntValue.toString());
			}

			devCerebralOximeter.setUhid(uhid);
			devCerebralOximeter.setStarttime(new Timestamp(deviceTime));

			try {
				patientDao.saveObject(devCerebralOximeter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public void saveApneaEventDetail(HashMap<String, Float> mappedMean, String uhid, Long deviceTime) {
		if (!BasicUtils.isEmpty(mappedMean) && !BasicUtils.isEmpty(uhid)) {
			if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_SPO2) && mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_HEART_RATE)) {
				Integer intSpo2Value = Math.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_SPO2));
				Integer intHrValue = Math
						.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_HEART_RATE));
				String babyDetailListQuery = "select obj from BabyDetail as obj where uhid='" + uhid + "'";
				List<BabyDetail> babyDetailList = patientDao.getListFromMappedObjNativeQuery(babyDetailListQuery);
				if (babyDetailList != null && babyDetailList.size() > 0) {
					String branchName = babyDetailList.get(0).getBranchname();
					if (branchName != null && branchName != "") {
						String refHospitalbranchnameQuery = "select obj from RefHospitalbranchname as obj where branchname='"
								+ branchName + "'";
						List<RefHospitalbranchname> refHospitalbranchnameList = patientDao
								.getListFromMappedObjNativeQuery(refHospitalbranchnameQuery);
						if (refHospitalbranchnameList != null && refHospitalbranchnameList.size() > 0) {
							Integer minApneaSpo2 = refHospitalbranchnameList.get(0).getSpo2();
							Integer minApneaHeartRate = refHospitalbranchnameList.get(0).getHr();
							if (minApneaSpo2 != null && intSpo2Value < minApneaSpo2) {
								
								ApneaEvent apneaEvent = new ApneaEvent();
								String eventType = "Desaturation";
								if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_HEART_RATE)) {
									
									if (minApneaHeartRate != null && intHrValue < minApneaHeartRate) {
										eventType = "Apnea";
									}
									apneaEvent.setHeartRate(intHrValue);
								}
								apneaEvent.setSpo2(intSpo2Value);

								if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_PULSE_RATE)) {
									Integer intPrValue = Math
											.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_PULSE_RATE));
									
//									if (minApneaHeartRate != null && intPrValue < minApneaHeartRate) {
//
//										eventType = "Apnea";
//									}
									apneaEvent.setPulseRate(intPrValue);
								}

								apneaEvent.setUhid(uhid);
								apneaEvent.setEventType(eventType);
								apneaEvent.setServerTime(new Timestamp(deviceTime));

								try {
									patientDao.saveObject(apneaEvent);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}else if (minApneaHeartRate != null && intHrValue < minApneaHeartRate) {
								ApneaEvent apneaEvent = new ApneaEvent();
								String eventType = "Bradycardia";
								if (!BasicUtils.isEmpty(intSpo2Value))
									apneaEvent.setSpo2(intSpo2Value);

								if (mappedMean.containsKey(DeviceConstants.DEVICE_MONITOR_PULSE_RATE)) {
									Integer intPrValue = Math
											.round(mappedMean.get(DeviceConstants.DEVICE_MONITOR_PULSE_RATE));
									
//									if (minApneaHeartRate != null && intPrValue < minApneaHeartRate) {
//
//										eventType = "Apnea";
//									}
									apneaEvent.setPulseRate(intPrValue);
								}

								apneaEvent.setUhid(uhid);
								apneaEvent.setEventType(eventType);
								apneaEvent.setServerTime(new Timestamp(deviceTime));
								apneaEvent.setHeartRate(intHrValue);

								try {
									patientDao.saveObject(apneaEvent);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public ResponseMessageObject importDeviceData(String uhid, String csvFile, String referenceDate) {
		ResponseMessageObject returnObj = new ResponseMessageObject();
		XSSFWorkbook workbook = null;
		try {
			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();
			if(!BasicUtils.isEmpty(referenceDate)) {
				returnObj.setMessage("Data imported successfully");
				returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
				System.out.println("reference Date" + referenceDate);
				System.out.println("offset" + offset);

				Date entryTimeStamp = CalendarUtility.getDateFormatGMT(CalendarUtility.CLIENT_CRUD_OPERATION)
						.parse(referenceDate);
				
				System.out.println("entryTimeStamp" + entryTimeStamp);

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
				String entryTimeServerFormat = formatter.format(entryTimeStamp);
				

				String beddevid = "";
				String babyObj = "select obj from BabyDetail obj where uhid='" + uhid + "'";
				List<BabyDetail> babyList = patientDao.getListFromMappedObjNativeQuery(babyObj);
				if(!BasicUtils.isEmpty(babyList)) {
					beddevid = babyList.get(0).getNicubedno();
				}
				CSVReader reader = null;
				reader = new CSVReader(new FileReader(csvFile));
	            String[] line;
	            int i = 0;
	            
				Timestamp previousDeviceTime = new Timestamp(System.currentTimeMillis());
	            while ((line = reader.readNext()) != null) {
	            	i++;
	            	if (i> 1) {
	            		List dataList = new ArrayList<String>();

						JSONObject json = new JSONObject(line[6]);
						dataList.add(json.get("data").toString());
						
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

						List timeList = new ArrayList<String>();
						if(i==2) {
							Timestamp timeDummy = new Timestamp(entryTimeStamp.getTime());
							System.out.println("timeDummy" + timeDummy);

							String entryTimeServerFormatDummy = formatter.format(timeDummy);
							System.out.println("entryTimeServerFormatDummy" + entryTimeServerFormatDummy);

							timeList.add(entryTimeServerFormatDummy);
							Date serverTimeInDate = dateFormat.parse(json.get("device_time").toString());
							System.out.println("serverTimeInDate" + serverTimeInDate);

							previousDeviceTime = new Timestamp(serverTimeInDate.getTime());
							
							System.out.println("previousDeviceTime" + previousDeviceTime);

						}else {
							Date serverTimeInDate = dateFormat.parse(json.get("device_time").toString());
							System.out.println("serverTimeInDate" + serverTimeInDate);

							long diffTime = serverTimeInDate.getTime() - previousDeviceTime.getTime();
							
							System.out.println("diffTime" + diffTime);

							Timestamp entryTimeStampNew = new Timestamp(entryTimeStamp.getTime() + diffTime); 
							
							System.out.println("entryTimeStampNew" + entryTimeStampNew);

							String entryTimeServerFormatNew = formatter.format(entryTimeStampNew);
							
							System.out.println("entryTimeServerFormatNew" + entryTimeServerFormatNew);

							timeList.add(entryTimeServerFormatNew);
						}
						String newboxName = line[3];
						if(line[3].equalsIgnoreCase("INVOS5100C")) {
							String usbDeviceType = json.get("Channel").toString();
							if((usbDeviceType.equalsIgnoreCase("Child") || usbDeviceType.equalsIgnoreCase("Parent")))
							newboxName = line[3] + usbDeviceType;
						}
		             	System.out.println("DEVICE PACKAGE :" + newboxName.trim());
						System.out.println("DEVICE LIST DATA :" + line[6].toString());
						System.out.println("SERVER TIME LIST :" + json.get("device_time").toString());
						
						String packageName = DeviceObjectMapper.deviceObjectNameMap.get(newboxName.trim().toLowerCase());
						try {
							if (!BasicUtils.isEmpty(uhid) && !BasicUtils.isEmpty(beddevid)) {
								Class<?> cls = Class.forName(packageName);
								Constructor<?> cons = cls.getConstructor(
										new Class[] { List.class, List.class, String.class, String.class });
								Object obj = cons.newInstance(dataList, timeList, uhid,
										beddevid);
								Method method = obj.getClass().getMethod("parse");
								method.invoke(obj);
							}
		
						} catch (ClassNotFoundException e) {
							returnObj.setMessage("Error" + e.toString());
							returnObj.setType(BasicConstants.MESSAGE_FAILURE);
							e.printStackTrace();
						} catch (InstantiationException e) {
							returnObj.setMessage("Error" + e.toString());
							returnObj.setType(BasicConstants.MESSAGE_FAILURE);
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							returnObj.setMessage("Error" + e.toString());
							returnObj.setType(BasicConstants.MESSAGE_FAILURE);
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							returnObj.setMessage("Error" + e.toString());
							returnObj.setType(BasicConstants.MESSAGE_FAILURE);
							e.printStackTrace();
						} catch (SecurityException e) {
							returnObj.setMessage("Error" + e.toString());
							returnObj.setType(BasicConstants.MESSAGE_FAILURE);
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							returnObj.setMessage("Error" + e.toString());
							returnObj.setType(BasicConstants.MESSAGE_FAILURE);
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							returnObj.setMessage("Error" + e.toString());
							returnObj.setType(BasicConstants.MESSAGE_FAILURE);
							e.printStackTrace();
						}
	            	}
	            }
			}
			
	
		}
		catch(Exception ex) {
			ex.printStackTrace();
			returnObj.setMessage("Error" + ex.toString());
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
	
			try {
				workbook.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				returnObj.setMessage("Error" + e1.toString());
				returnObj.setType(BasicConstants.MESSAGE_FAILURE);
		
			}
		}
		return returnObj;
	}

	@Override
	public void saveVentilatorDetail(HashMap<String, Float> mappedMean, String uhid, String beddeviceid,
									 Long deviceTime) {
		if (!BasicUtils.isEmpty(uhid)) {
			DeviceVentilatorData vent = new DeviceVentilatorData();
			DashboardDeviceVentilatorDetail vent2 = new DashboardDeviceVentilatorDetail();

			if (mappedMean.containsKey(beddeviceid)) {
				vent.setBeddeviceid(beddeviceid);
				vent2.setBeddeviceid(beddeviceid);
			}

			// New Mapping
			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_COMPLIANCE)) {
				Float floatValue = mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_COMPLIANCE);
				vent.setC(floatValue.toString());
				vent2.setC(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_RESISTANCE)) {
				Float floatValue = mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_RESISTANCE);
				vent.setR(floatValue.toString());
				vent2.setR(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_TC)) {
				Float floatValue = mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_TC);
				vent.setTc(floatValue.toString());
				vent2.setTc(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_C20)) {
				Float floatValue = mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_COMPLIANCE);
				vent.setC20(floatValue.toString());
				vent2.setC20(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_TRIGGER)) {
				Float floatValue = mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_TRIGGER);
				vent.setTrigger(floatValue.toString());
				vent2.setTrigger(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_RVR)) {
				Float floatValue = mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_RVR);
				vent.setRvr(floatValue.toString());
				vent2.setRvr(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_TI)) {
				Float floatValue = mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_TI);
				vent.setTi(floatValue.toString());
				vent2.setTi(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_TE)) {
				Float floatValue = mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_TE);
				vent.setTe(floatValue.toString());
				vent2.setTe(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_MAP)) {
				Float floatValue = mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_MAP);
				vent.setMap(floatValue.toString());
				vent2.setMap(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_PEEP)) {
				Float floatValue = (mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_PEEP));
				vent.setPeep(floatValue.toString());
				vent2.setPeep(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_PIP)) {
				Float floatValue = (mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_PIP));
				vent.setPip(floatValue.toString());
				vent2.setPip(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_DCO2)) {
				Float floatValue = mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_DCO2);
				vent.setDco2(floatValue.toString());
				vent2.setDco2(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_VTIM)) {
				Float floatValue = mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_VTIM);
				vent.setVtim(floatValue.toString());
				vent2.setVtim(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_VTHF)) {
				Float floatValue = mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_VTHF);
				vent.setVthf(floatValue.toString());
				vent2.setVthf(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_VT)) {
				Float floatValue = mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_VT);
				vent.setVt(floatValue.toString());
				vent2.setVt(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_LEAK)) {
				Float floatValue = mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_LEAK);
				vent.setLeak(floatValue.toString());
				vent2.setLeak(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_SPONT)) {
				Float floatValue = mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_SPONT);
				vent.setSpont(floatValue.toString());
				vent2.setSpont(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_MV)) {
				Float floatValue = mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_MV);
				vent.setMv(floatValue.toString());
				vent2.setMv(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_MVIM)) {
				Float floatValue = mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_MVIM);
				vent.setMvim(floatValue.toString());
				vent2.setMvim(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_RATE)) {
				Float floatValue = (mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_RATE));
				vent.setRate(floatValue.toString());
				vent2.setRate(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_SPO2)) {
				Float floatValue = (mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_SPO2));
				vent.setSpo2(floatValue.toString());
				vent2.setSpo2(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_FIO2)) {
				Float floatValue = (mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_FIO2));
				vent.setFio2(floatValue.toString());
				vent2.setFio2(floatValue.toString());
			}

			// Old Mapping
			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_FLOWPERMIN)) {
				Float floatValue = (mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_FLOWPERMIN));
				vent.setFlowpermin(floatValue.toString());
				vent2.setFlowpermin(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_FREQRATE)) {
				Float floatValue = (mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_FREQRATE));
				vent.setFreqrate(floatValue.toString());
				vent2.setFreqrate(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_MEANBP)) {
				Float floatValue = (mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_MEANBP));
				vent.setMeanbp(floatValue.toString());
				vent2.setMeanbp(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_MINVOL)) {
				Float floatValue = (mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_MINVOL));
				vent.setMinvol(floatValue.toString());
				vent2.setMinvol(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_NOPPM)) {
				Float floatValue = (mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_NOPPM));
				vent.setNoppm(floatValue.toString());
				vent2.setNoppm(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_OCCPRESSURE)) {
				Float floatValue = (mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_OCCPRESSURE));
				vent.setOccpressure(floatValue.toString());
				vent2.setOccpressure(floatValue.toString());
			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_PEAKBP)) {
				Float floatValue = (mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_PEAKBP));
				vent.setPeakbp(floatValue.toString());
				vent2.setPeakbp(floatValue.toString());
			}

//			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_PEEP)) {
//				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_PEEP));
//				vent.setPeep(intValue.toString());
//				vent2.setPeep(intValue.toString());
//			}
//
//			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_PIP)) {
//				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_PIP));
//				vent.setPip(intValue.toString());
//				vent2.setPip(intValue.toString());
//			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_PLATPRESSURE)) {
				Float floatValue = (mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_PLATPRESSURE));
				vent.setPlatpressure(floatValue.toString());
				vent2.setPlatpressure(floatValue.toString());
			}

//			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_TI)) {
//				Integer intValue = Math.round(mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_TI));
//				vent.setTi(intValue.toString());
//				vent2.setTi(intValue.toString());
//			}

			if (mappedMean.containsKey(DeviceConstants.DEVICE_VENTILATOR_TIDALVOL)) {
				Float floatValue = (mappedMean.get(DeviceConstants.DEVICE_VENTILATOR_TIDALVOL));
				vent.setTidalvol(floatValue.toString());
				vent2.setTidalvol(floatValue.toString());
			}

			vent.setUhid(uhid);
			vent2.setUhid(uhid);
			vent.setStart_time(new Timestamp(new Date().getTime()));
			vent2.setStart_time(new Timestamp(new Date().getTime()));

			try {
				patientDao.saveObject(vent);
				patientDao.saveObject(vent2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getUhidFromBoxName(String boxname) {
		String uhid = null;
		String query = "select uhid from bed_device_detail " + "where bbox_device_id = ( "
				+ "SELECT bbox_id FROM ref_inicu_bbox WHERE bboxname = '" + boxname + "') and status = true";

		List<String> resultSet = patientDao.getListFromNativeQuery(query);
		if (!BasicUtils.isEmpty(resultSet)) {
			uhid = resultSet.get(0).toString();
		}
		return uhid;
	}

	@Override
	public void pushKafkaDataToCassandra(String uhid, JSONObject pushObject, String deviceTime, String deviceType) {
		try {
			Date date = new Date();
			String data = pushObject.get(Constants.DEVICE_DATA_JSON_KEY).toString();
			patientDataExecutor.execute(new InsertDataThread(date, data, uhid, true, deviceType));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * method used for sending data from box for kdah using box data with KDAH.
	 */
	@Override
	public void saveDeviceVentDetailKdah(HashMap<String, Float> mappedMean, String uhid, String beddeviceid,
										 Long deviceTime) {
		// TODO Auto-generated method stub
		saveVentilatorDetail(mappedMean, uhid, beddeviceid, deviceTime);
	}

	/**
	 * method used to pilot phase Ventilator data gets directly saved to postgres.
	 * This can be used to quick visualizing data on the screen from the device.
	 * <p>
	 * Please note that the data receiving frequency should be one row per minute,
	 * for optimum and stable usage.
	 */
	@Override
	public void saveVentDataToPilotPostgres(String jsonData) {
		try {
			JSONObject jsonObj = new JSONObject(jsonData);
			if (!BasicUtils.isEmpty(jsonObj.get("device_name"))) {
				String packageName = DeviceObjectMapper.deviceObjectNameMap.get(jsonObj.get("device_name").toString());
				if (!BasicUtils.isEmpty(packageName)) {
					// get bedid on the basis of uhid.
					// String pilotUhid =
					// BasicConstants.props.getProperty("pilot_uhid");
					String beddevid = "";
					String pilotUhid = "";
					// get uhid from the bed id.
					String hl7bedid = BasicConstants.props.getProperty("pilot_bedid");
					// fetch uhid from the hl7bedid.
					if (!BasicUtils.isEmpty(hl7bedid)) {
						String fetchUhid = "SELECT obj FROM Hl7DeviceMapping as obj WHERE obj.hl7Bedid='" + hl7bedid
								+ "' AND " + "isactive='true'";

						List<Hl7DeviceMapping> resultSet = patientDao.getListFromMappedObjNativeQuery(fetchUhid);
						if (!BasicUtils.isEmpty(resultSet)) {
							Hl7DeviceMapping obj = resultSet.get(0);
							pilotUhid = obj.getUhid();
						}
					}

					if (!BasicUtils.isEmpty(pilotUhid) && !BasicUtils.isEmpty(jsonObj.get("data"))) {
						// fetch beddevid based on integration type.
						HashMap<String, String> integrationTypeMap = userPanelService.getAddSettingPreference();
						if (!BasicUtils.isEmpty(integrationTypeMap)) {
							String integrationType = integrationTypeMap.get("integration_type");
							if (!BasicUtils.isEmpty(integrationType)) {
								// fetch beddeviceid based on the integration
								// type.
								// bed device id needs to be set only in the
								// case of inicu.
								if (integrationType.equalsIgnoreCase("inicu")) {
									String fetchbeddevid = "SELECT bedid FROM bed_device_detail WHERE uhid = '"
											+ pilotUhid.trim() + "' " + "AND status = true";

									List<Object> result = patientDao.getListFromNativeQuery(fetchbeddevid);
									if (!BasicUtils.isEmpty(result)) {
										beddevid = result.get(0).toString();
									}
								}
							}
						}

						JSONObject dataObj = new JSONObject(jsonObj.get("data"));
						List<String> data = new ArrayList<>();
						data.add(jsonObj.get("data").toString());

						Class<?> cls = Class.forName(packageName);
						Constructor<?> cons = cls
								.getConstructor(new Class[]{List.class, String.class, String.class});
						Object obj = cons.newInstance(data, pilotUhid, beddevid);
						Method method = obj.getClass().getMethod("parse");
						method.invoke(obj);

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveMutlipleBloodGasDeviceDataPostgres(String jsonArrayData) {
		// TODO Auto-generated method stub
		try {
			JSONArray jsonArray = new JSONArray(jsonArrayData);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonData = (JSONObject) jsonArray.getJSONObject(i);
				System.out.println("JSON Blood Gas Data To Be Saved = " + jsonData.toString());
				saveDeviceDataPostgres(jsonData.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void saveDeviceDataPostgres(String jsonData) {
		// data to be received from blood gas machines.
		// this is handled separately because all other devices are govern by
		// bedid and uhid.
		// blood gas machine will not have any bedid or uhid a ssociated with it.

		try {
			JSONObject obj = new JSONObject(jsonData);
			String hl7Message = obj.get("data").toString();
			if (!BasicUtils.isEmpty(hl7Message)) {
				// fetch sample id and save hl7 Message

				if (obj.get("device_name").equals("GEM")) {
					NursingBloodGas bloodgas = new NursingBloodGas();
					if (obj.has("beecf"))
						bloodgas.setBe_ecf((String) obj.get("beecf"));
					if (obj.has("glu"))
						bloodgas.setGlucose((String) obj.get("glu"));
					if (obj.has("na+"))
						bloodgas.setNa((String) obj.get("na+"));
					if (obj.has("k+"))
						bloodgas.setK((String) obj.get("k+"));
					if (obj.has("pcO2"))
						bloodgas.setPco2((String) obj.get("pcO2"));
					if (obj.has("SO2c"))
						bloodgas.setSpo2((String) obj.get("SO2c"));
					if (obj.has("ph"))
						bloodgas.setPh((String) obj.get("ph"));
					if (obj.has("pO2"))
						bloodgas.setPo2((String) obj.get("pO2"));
					if (obj.has("lac"))
						bloodgas.setLactate((String) obj.get("lac"));
					if (obj.has("HCO3std"))
						bloodgas.setHco2((String) obj.get("HCO3std"));
					if (obj.has("hco3"))
						bloodgas.setRegular_hco3((String) obj.get("hco3"));
					if (obj.has("be"))
						bloodgas.setBe((String) obj.get("be"));
					if (obj.has("a++"))
						bloodgas.setIonized_calcium((String) obj.get("ca++"));
					if (obj.has("hct"))
						bloodgas.setHct((String) obj.get("hct"));
					if (obj.has("thbc"))
						bloodgas.setThbc((String) obj.get("thbc"));
					if (obj.has("sampletype")) {
						String type = (String) obj.get("sampletype");
						if (type.equals("A")) {
							bloodgas.setSampleType("Arterial");
						}
						if (type.equals("V")) {
							bloodgas.setSampleType("Venous");
						}
					}

					bloodgas.setLoggeduser("test");

					String babyname = (String) obj.get("babyname");
					String sampledate = (String) obj.get("sampledate");
					if (!BasicUtils.isEmpty(sampledate)) {
						Timestamp today = Timestamp.valueOf(sampledate);

						bloodgas.setUserDate((String) obj.get("date"));
						String meridian = "AM";
						if (today.getHours() >= 12) {
							meridian = "PM";
						}
						String nn_blood_time = today.getHours() + ":" + today.getMinutes() + ":" + meridian;
						bloodgas.setNnBloodgasTime(nn_blood_time);

						String zone_time = "GMT+5:30";
						int offset = TimeZone.getTimeZone(zone_time).getRawOffset()
								- TimeZone.getDefault().getRawOffset();

						System.out.println("offset = " + offset);

						bloodgas.setEntryDate((new Timestamp(today.getTime() + offset)));
						bloodgas.setCreationtime((new Timestamp(today.getTime() + offset)));
						bloodgas.setModificationtime((new Timestamp(today.getTime() + offset)));
						bloodgas.setUhid((String) obj.get("uhid"));

						patientDao.saveObject(bloodgas);
					}

				} else {
					NursingBloodGas bloodgas = new NursingBloodGas();
					String dateStr = "";
					String dateStrTemp = "";

					String[] strArr = hl7Message.split("\\|");
					for (int index = 0; index < strArr.length; index++) {
						String str = strArr[index];
						// System.out.println(str + " there");
						if (strArr[index].contains("PID") && strArr[index + 1].contains("1")) {
							System.out.println(strArr[index + 3] + " UHID");
							bloodgas.setUhid(strArr[index + 3]);
						}
						if (strArr[index].contains("OBR") && strArr[index + 1].contains("1")) {
							System.out.println(strArr[index + 4] + " Method");
							bloodgas.setSampleMethod(strArr[index + 4]);
						}
						if (strArr[index].contains("pH^M")) {
							System.out.println(strArr[index + 2] + " pH");
							bloodgas.setPh(strArr[index + 2]);
						}
						if (strArr[index].contains("pCO2^M")) {
							System.out.println(strArr[index + 2] + " pCO2");
							bloodgas.setPco2(strArr[index + 2]);
						}
						if (strArr[index].contains("pO2^M")) {
							System.out.println(strArr[index + 2] + " pO2");
							bloodgas.setPo2(strArr[index + 2]);
						}
						if (strArr[index].contains("Na+")) {
							System.out.println(strArr[index + 2] + " Na+");
							bloodgas.setNa(strArr[index + 2]);
						}
						if (strArr[index].contains("Glu")) {
							System.out.println(strArr[index + 2] + " Glu");
							bloodgas.setGlucose(strArr[index + 2]);
						}
						if (strArr[index].contains("K+^M")) {
							System.out.println(strArr[index + 2] + " K+");
							bloodgas.setK(strArr[index + 2]);
						}
						if (strArr[index].contains("Cl-")) {
							System.out.println(strArr[index + 2] + " Cl-");
							bloodgas.setCl(strArr[index + 2]);
						}
						if (strArr[index].contains("Ca++")) {
							System.out.println(strArr[index + 2] + " Ca++");
							bloodgas.setIonized_calcium(strArr[index + 2]);
						}
						if (strArr[index].contains("Lac")) {
							System.out.println(strArr[index + 2] + " Lac");
							bloodgas.setLactate(strArr[index + 2]);
						}
						if (strArr[index].contains("gap^C")) {
							System.out.println(strArr[index + 2] + " gap^C");
							bloodgas.setAnionGap(strArr[index + 2]);
						}
						if (strArr[index].contains("SBE^C")) {
							System.out.println(strArr[index + 2] + " BE^C");
							bloodgas.setBe_ecf(strArr[index + 2]);
						}
						if (strArr[index].contains("SBC^C")) {
							System.out.println(strArr[index + 2] + " HCO-3");
							bloodgas.setHco2(strArr[index + 2]);
						}
						if (strArr[index].contains("Osm^C")) {
							System.out.println(strArr[index + 2] + " Osm^C");
							bloodgas.setOsmolarity(strArr[index + 2]);
						}
						if (strArr[index].contains("sO2^M")) {
							System.out.println(strArr[index + 2] + " sO2^M");
							bloodgas.setSpo2(strArr[index + 2]);
						}
						if (strArr[index].equals("L") && strArr[index + 3].equals("F") && dateStr != null
								&& dateStr.length() <= 0) {
							dateStr = strArr[index + 6];
						}
						if (strArr[index].equals("H") && strArr[index + 3].equals("F") && dateStr != null
								&& dateStr.length() <= 0) {
							dateStr = strArr[index + 6];
						}
						if (strArr[index].equals("N") && strArr[index + 3].equals("F") && dateStr != null
								&& dateStr.length() <= 0) {
							dateStr = strArr[index + 6];
						}
						if (strArr[index].contains("Cord Blood")) {
							System.out.println("Cord Blood Venous");
							bloodgas.setSampleType("Cord Blood Venous");
						} else if (strArr[index].contains("Arterial")) {
							System.out.println("Arterial");
							bloodgas.setSampleType("Arterial");
						} else if (strArr[index].contains("Venous")) {
							System.out.println("Venous");
							bloodgas.setSampleType("Venous");
						}
					}

					if (BasicUtils.isEmpty(dateStr)) {
						dateStr = dateStrTemp;
					}
					bloodgas.setLoggeduser("test");

					if (!BasicUtils.isEmpty(dateStr)) {
						System.out.println(dateStr + " date");

						Timestamp today = null;
						String year = dateStr.charAt(0) + "" + dateStr.charAt(1) + "" + dateStr.charAt(2) + ""
								+ dateStr.charAt(3);
						String month = dateStr.charAt(4) + "" + dateStr.charAt(5);
						String date = dateStr.charAt(6) + "" + dateStr.charAt(7);
						String hour = dateStr.charAt(8) + "" + dateStr.charAt(9);
						String min = dateStr.charAt(10) + "" + dateStr.charAt(11);
						String sec = dateStr.charAt(12) + "" + dateStr.charAt(13);
						String entryDate = year + "-" + month + "-" + date + " " + hour + ":" + min + ":" + sec;

						today = Timestamp.valueOf(entryDate);
						String dateString = year + "-" + month + "-" + date;

						System.out.println(today);
						System.out.println(dateString);

						bloodgas.setUserDate(dateString);
						String meridian = "AM";
						if (today.getHours() >= 12) {
							meridian = "PM";
						}
						String nn_blood_time = today.getHours() + ":" + today.getMinutes() + ":" + meridian;
						bloodgas.setNnBloodgasTime(nn_blood_time);

						String zone_time = "GMT+5:30";
						int offset = TimeZone.getTimeZone(zone_time).getRawOffset()
								- TimeZone.getDefault().getRawOffset();

						System.out.println(offset + "offset");

						bloodgas.setEntryDate((new Timestamp(today.getTime() + offset)));
						bloodgas.setCreationtime((new Timestamp(today.getTime() + offset)));
						bloodgas.setModificationtime((new Timestamp(today.getTime() + offset)));

						System.out.println(bloodgas.toString());
						patientDao.saveObject(bloodgas);
					}

					if (hl7Message.contains("MSH")) {
						String[] msgArr = hl7Message.split("MSH");
						if (msgArr.length > 1) {
							String hl7 = "MSH" + msgArr[1].toString();

							System.out.println("MESSAGE: " + hl7);

							if (hl7.contains("PID")) {
								String[] arr = hl7.split("PID");
								// System.out.println(arr[1]);
								if (arr.length > 1) {
									int counter = 0;
									for (int i = 0; i < arr[1].length(); i++) {
										if (arr[1].charAt(i) == '|') {
											counter++;
										}
									}
									System.out.println("counter :" + counter);

									String[] val = arr[1].split("\\|", counter + 1);
									String sampleid = (val[3]);
									System.out.println(sampleid);

									if (!BasicUtils.isEmpty(sampleid)) {

										// check if sample id already exists or not.

										String query = "SELECT obj FROM DeviceBloodgasDetailDetail as obj WHERE sampleId='"
												+ sampleid.trim() + "'";
										List<DeviceBloodgasDetailDetail> resultSet = patientDao
												.getListFromMappedObjNativeQuery(query);
										if (BasicUtils.isEmpty(resultSet)) {
											DeviceBloodgasDetailDetail bloodgasObj = new DeviceBloodgasDetailDetail();
											bloodgasObj.setHl7Message(hl7Message);
											bloodgasObj.setSampleId(sampleid.trim());

											patientDao.saveObject(bloodgasObj);
										} else {
											System.out.println("blood gas report already exists");
										}
									}
								}
							}
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	Float getValue(String parameter, String substringDelimiter, char delimiter, String hl7Message) {
		Float value = null;
		try {
			String[] valArr = hl7Message.split(parameter);
			if (valArr != null && valArr.length > 1) {
				String valWithPipe = valArr[1].substring(0, valArr[1].indexOf(substringDelimiter));
				int counter = getStringLengthWithDelimiter(valWithPipe, delimiter);
				String[] val = valWithPipe.split("\\|", counter + 1);
				value = Float.parseFloat(val[counter - 1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;
	}

	int getStringLengthWithDelimiter(String str, char delimiter) {
		int counter = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == delimiter) {
				counter++;
			}
		}
		return counter;
	}

	@Override
	public String getCameraUhid(String boxName) {
		String uhid = null;
		String queryForBoxId = "select bbox_id FROM ref_inicu_bbox WHERE bboxname = '" + boxName + "'";
		List<BigInteger> boxIdListFromQuery = inicuDao.getListFromNativeQuery(queryForBoxId);
		if (!BasicUtils.isEmpty(boxIdListFromQuery)) {
			BigInteger boxId = boxIdListFromQuery.get(0);
			String queryForUhid = "select uhid from bed_device_detail where bbox_device_id = " + boxId
					+ " AND status = true";
			List<String> uhidListFromQuery = inicuDao.getListFromNativeQuery(queryForUhid);
			if (!BasicUtils.isEmpty(uhidListFromQuery)) {
				uhid = uhidListFromQuery.get(0);
			}
		}
		return uhid;
	}

	@Override
	public String getCapturingStatus(String uhid) {
		String isStartCapturing = "false";
		if (BasicConstants.isCapturingMap.containsKey(uhid)) {
			isStartCapturing = BasicConstants.isCapturingMap.get(uhid).toString();
		}
		return isStartCapturing;
	}

	@Override
	public void setCapturingStatus(JSONObject jsonCapturingStatus) {
		try {
			if (jsonCapturingStatus.has("uhid")) {
				String uhid = jsonCapturingStatus.getString("uhid");
				if (!BasicUtils.isEmpty(uhid)) {
					Boolean capturingStatus = jsonCapturingStatus.getBoolean("capturingStatus");
					BasicConstants.isCapturingMap.put(uhid, capturingStatus);
				}
				System.out.println("Capturing Status of " + uhid + " is " + BasicConstants.isCapturingMap.get(uhid));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveImageReceivedFromBoxForCamera(JSONObject jsonData) {
		// TODO Auto-generated method stub
		try {
			if (jsonData.has("uhid")) {
				String uhid = jsonData.getString("uhid");
				if (!BasicUtils.isEmpty(uhid)) {
					String image_data = jsonData.getString("image_data");
					List<String> cameraImageList = new ArrayList<>();
					if (!BasicUtils.isEmpty(BasicConstants.cameraListMap.get(uhid))) {
						cameraImageList = BasicConstants.cameraListMap.get(uhid);
					}
					cameraImageList.add(image_data);
					BasicConstants.cameraListMap.put(uhid, cameraImageList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getCameraFeed(String uhid) {
		List<String> videoURL = new ArrayList<>();
		List<String> cameraFeedList = new ArrayList<>();
		String videoURLStr = "";
		try {
			if (!BasicUtils.isEmpty(uhid)) {
				String query = "select obj from BabyDetail as obj where uhid='" + uhid + "'";
				List<BabyDetail> babyData = patientDao.getListFromMappedObjNativeQuery(query);
				if (!BasicUtils.isEmpty(babyData)) {
					String branchName = babyData.get(0).getBranchname();

					String hospitalBranchId = "";
					query = "select obj from RefHospitalbranchname as obj where branchname='" + branchName + "'";
					List<RefHospitalbranchname> hospitalDetails = patientDao.getListFromMappedObjNativeQuery(query);
					if (!BasicUtils.isEmpty(hospitalDetails)) {
						hospitalBranchId = hospitalDetails.get(0).getHospitalbranchid();
					}
					String[] hospitalNames = branchName.split(",");

					String fetchDeviceNameDetails = "SELECT upper(b.tinyboxname),a.uhid,a.deviceid FROM bed_device_detail a " +
							"INNER JOIN ref_inicu_bbox as b " +
							"ON a.bbox_device_id=b.bbox_id " +
							"WHERE a.uhid = '" + uhid + "' and a.status = 'true' and (b.bboxname is null or b.bboxname = '')";
					List<Object[]> deviceBoxName = inicuDao.getListFromNativeQuery(fetchDeviceNameDetails);
					if (!BasicUtils.isEmpty(deviceBoxName)) {
						Object[] object = deviceBoxName.get(0);
						//String boxName = object[0] != null ? object[0].toString() : "";
						String tinyboxName = object[0] != null ? object[0].toString() : "";
						String deviceName = "";
						if (!BasicUtils.isEmpty(tinyboxName)) {
							deviceName = tinyboxName.replace("-", "");
							deviceName = deviceName.replace(" ", "");
						}

						// Changing this code to enable/disable audio
						String deviceNameStr = deviceName;

						deviceName = deviceName + ".stream_720p";

						cameraFeedList.add(BasicConstants.props.getProperty(BasicConstants.PROP_WOWZA_SDP_URL));
						cameraFeedList.add(BasicConstants.SCHEMA_NAME + "_" + hospitalBranchId);
						cameraFeedList.add(deviceName);
						cameraFeedList.add(deviceNameStr);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cameraFeedList;
	}

	@Override
	public void deleteCameraFeed(String timeout) {
		String query = "DELETE FROM camera_feed WHERE creationtime < NOW() - INTERVAL '" + timeout + "' minute";
//		patientDao.updateOrDeleteNativeQuery(query);
	}

	@Override
	public ResponseMessageObject addMacidToDeviceDetail(String macid) {
		// TODO Auto-generated method stub
		ResponseMessageObject resp = new ResponseMessageObject();
		MacID mac = new MacID();
		mac.setMacid(macid);
		mac.setAvailable(true);
		boolean flag_device_not_Set = false;
		try {
			boolean macexist = macidExistsInMacIDTable(macid);
			if (!macexist) {
				System.out.println("mac does not exist. create entry in table");
				patientDao.saveObject(mac);

				resp.setMessage("added");
				resp.setType(RegistrationConstants.REGISTERATION_SUCCESSFULL);
			} else {
				System.out.println("already saved");
				DeviceDetail dev = new DeviceDetail();
				String query = "select obj from DeviceDetail as obj where ipaddress='" + macid + "'";
				List<DeviceDetail> lisDevice = patientDao.getListFromMappedObjNativeQuery(query);
				String serial = null;
				try {
					serial = lisDevice.get(0).getDeviceserialno();
				} catch (IndexOutOfBoundsException exp) {
					System.out.println("Please add device from Admin");
					flag_device_not_Set = true;
					resp.setMessage(RegistrationConstants.DEVICE_NOT_SET_FROM_ADMIN);
					resp.setType(RegistrationConstants.REGISTERATION_SUCCESSFULL);
				}
				if (!flag_device_not_Set) {
					System.out.println("serial " + serial);
					resp.setMessage(serial);
					resp.setType(RegistrationConstants.NEO_SERIAL);
				}
				/*
				 * DeviceDetail dev = new DeviceDetail(); String query =
				 * "select obj from DeviceDetail as obj where ipaddress='" + macid + "'";
				 * List<DeviceDetail> lisDevice =
				 * patientDao.getListFromMappedObjNativeQuery(query);
				 *
				 * JSONObject setting = new JSONObject();
				 * System.out.println("lisDevice.get(0).getDevicebrand()" +
				 * lisDevice.get(0).getDevicebrand());
				 * System.out.println("lisDevice.get(0).getDevicename()" +
				 * lisDevice.get(0).getDevicename()); setting.put(DeviceConstants.DEVICE_BRAND,
				 * lisDevice.get(0).getDevicebrand()); setting.put(DeviceConstants.DEVICE_NAME,
				 * lisDevice.get(0).getDevicename()); System.out.println("setting " +
				 * setting.toString()); resp.setMessage(setting.toString());
				 * resp.setType(RegistrationConstants. REGISTERATION_ALREADY_EXISTS);
				 */
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public String getDeviceId(String message) {
		String deviceid = null;
		// TODO Auto-generated method stub
		JSONObject json;
		try {
			json = new JSONObject(message);
			System.out.println("got " + message);
			String devicename = json.getString("devicename");
			String brandname = json.getString("brandname");
			String query = "select deviceid from InicuDevice where devicename='" + devicename + "' and brandname='"
					+ brandname + "'";
			List<InicuDevice> data = patientDao.getListFromMappedObjNativeQuery(query);
			if (!BasicUtils.isEmpty(data)) {

				// System.out.println("data.get(0).getDeviceid()
				// "+data.get(0).getDeviceid());
				deviceid = String.valueOf(data.get(0));
				System.out.println("hey " + "sending setting " + deviceid);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return deviceid;
	}

	@Override
	public String getDeviceSettingForProgram(String macid) {
		// TODO Auto-generated method stub
		/*
		 * DeviceDetail dev = new DeviceDetail(); String query =
		 * "select obj from DeviceDetail as obj where ipaddress='" + macid + "'";
		 * List<DeviceDetail> lisDevice =
		 * patientDao.getListFromMappedObjNativeQuery(query);
		 *
		 * String deviceid;
		 *
		 * deviceid = String.valueOf(lisDevice.get(0).getInicu_deviceid());
		 *
		 * System.out.println("setting " + deviceid);
		 */
		// return deviceid;
		DeviceDetail dev = new DeviceDetail();
		String query = "select obj from DeviceDetail as obj where ipaddress='" + macid + "'";
		List<DeviceDetail> lisDevice = patientDao.getListFromMappedObjNativeQuery(query);

		JSONObject setting = new JSONObject();
		System.out.println("lisDevice.get(0).getDevicebrand()" + lisDevice.get(0).getDevicebrand());
		System.out.println("lisDevice.get(0).getDevicename()" + lisDevice.get(0).getDevicename());
		try {
			setting.put(DeviceConstants.DEVICE_BRAND, lisDevice.get(0).getDevicebrand());
			setting.put(DeviceConstants.DEVICE_NAME, lisDevice.get(0).getDevicename());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("setting " + setting.toString());
		String deviceid = getDeviceId(setting.toString());
		return deviceid;

	}

	@Override
	public void saveDeviceDataException(JSONObject jsonData) {
		System.out.println("device data exception to be saved " + jsonData);
		try {
			if (jsonData.has(RegistrationConstants.NEO_SERIAL)) {
				String boxSerialNo = jsonData.getString(RegistrationConstants.NEO_SERIAL);
				if (!BasicUtils.isEmpty(boxSerialNo)) {
					DeviceException deviceException = new DeviceException();
					deviceException.setExceptionmessage(jsonData.getString("exceptionmessage"));
					deviceException.setDeviceserialno(boxSerialNo);
					patientDao.saveObject(deviceException);
				}
			}
		} catch (JSONException e) {
			System.out.println("device data exception-- invalid json");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public DeviceDetail getDeviceDetail(String boxSerialNo, String boardName) {
		DeviceDetail deviceDetailObject = new DeviceDetail();
		try {
			String queryToFetchDeviceDetail = "SELECT obj FROM DeviceDetail as obj WHERE "
					+ "deviceserialno='" + boxSerialNo + "' and description='" + boardName + "'";
			List<DeviceDetail> deviceDetailList = patientDao.getListFromMappedObjNativeQuery(queryToFetchDeviceDetail);
			if (!BasicUtils.isEmpty(deviceDetailList)) {
				deviceDetailObject = deviceDetailList.get(0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deviceDetailObject;
	}


	@Override
	public InicuServerTimeJson getServerTime() {
		InicuServerTimeJson jsonObject = new InicuServerTimeJson();
		try {
			Long currentServerTimeInLong = (Long) (new Date().getTime());
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
			Timestamp currentServerTimeInTimestamp = new Timestamp(currentServerTimeInLong);
//			currentServerTimeInTimestamp.setNanos(0);
			System.out.println("Current Server Time In Long = " + currentServerTimeInLong);
			System.out.println("Current Server Time In Timestamp = " + dateFormat.format(currentServerTimeInTimestamp));
			jsonObject.setCurrentServerTime(dateFormat.format(currentServerTimeInTimestamp));

			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();
			jsonObject.setOffset(offset);

			ResponseMessageObject response = new ResponseMessageObject();
			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("Current Server Time is Fetched Properly");
			jsonObject.setResponse(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ResponseMessageObject response = new ResponseMessageObject();
			response.setType(BasicConstants.MESSAGE_FAILURE);
			response.setMessage("Current Server Time is not Fetched Properly Due to Internet Error.");
			e.printStackTrace();
		}
		return jsonObject;
	}

	public String getViewTimingMessage(BabyViewTimings lastObject){

		String morningFromTimeMsgStr = BasicUtils.getTimeString(lastObject.getMorningFromTime());
		String morningToTimeMsgStr = BasicUtils.getTimeString(lastObject.getMorningToTime());

		String eveningFromTimeMsgStr = BasicUtils.getTimeString(lastObject.getEveningFromTime());
		String eveningToTimeMsgStr = BasicUtils.getTimeString(lastObject.getEveningToTime());

		String message = "Kindly view your baby remotely during ";

		if(!BasicUtils.isEmpty(lastObject.getMorningTimeEnabled()) && lastObject.getMorningTimeEnabled() == true){
			message += morningFromTimeMsgStr +" to " + morningToTimeMsgStr +" in morning";
		}

		if(!BasicUtils.isEmpty(lastObject.getEveningTimeEnabled()) && lastObject.getEveningTimeEnabled() == true){
			if(lastObject.getMorningTimeEnabled() == true){
				message += " and ";
			}
			message += eveningFromTimeMsgStr +" to " + eveningToTimeMsgStr +" in evening";
		}
		message += ".";

		return message;
	}

	@Override
	public void saveQuestionnaireResult(QuestionnaireResult questionnaireResult) {
			try{
				inicuDao.saveObject(questionnaireResult);
			}catch (Exception e){
				e.printStackTrace();
			}
	}
}
