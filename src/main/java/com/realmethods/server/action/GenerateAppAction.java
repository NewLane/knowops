/*******************************************************************************
 * realMethods Confidential
 * 
 * 2021 realMethods, Inc.
 * All Rights Reserved.
 * 
 * This file is subject to the terms and conditions defined in
 * file 'license.txt', which is part of this source code package.
 *  
 * Contributors :
 *       realMethods Inc - General Release
 ******************************************************************************/
package com.realmethods.server.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.realmethods.api.GitParams;
import com.realmethods.codetemplate.BuildApp;
import com.realmethods.codetemplate.GenerateApp;
import com.realmethods.codetemplate.GenerateAppOptions;
import com.realmethods.codetemplate.GenerateAppStats;
import com.realmethods.entity.Build;
import com.realmethods.entity.BuildStatus;
import com.realmethods.entity.FrameworkPackage;
import com.realmethods.entity.GeneratedAppDetails;
import com.realmethods.entity.Project;
import com.realmethods.entity.ScopeType;
import com.realmethods.entity.dao.BuildDAO;
import com.realmethods.entity.dao.FrameworkPackageDAO;
import com.realmethods.entity.dao.GeneratedAppDetailsDAO;
import com.realmethods.entity.dao.ProjectDAO;
import com.realmethods.exception.GenerationException;
import com.realmethods.foundational.common.exception.FrameworkDAOException;
import com.realmethods.foundational.common.namespace.FrameworkNameSpace;
import com.realmethods.common.helpers.AWSHelper;
import com.realmethods.common.helpers.AppGenHelper;

/**
 * Struts action class to handle application creation, which may include
 * building the app, deploying the app, and persisting the app. Method exec()
 * does is the primary handler.
 * 
 * @author realMethods, Inc.
 * 
 */

public class GenerateAppAction extends BaseStrutsAction {
	/**
	 * default constructor
	 */
	public GenerateAppAction() {
		// no_op
	}

	/**
	 * Assign the GitParams field with the provided argument.
	 * 
	 * @param GitParams
	 */
	public void setGitParams( GitParams params ) {
		this.gitParams = params;
		LOGGER.info("gitParams assigned are: " + gitParams );
	}
	/**
	 * Assign the packageId field with the provided argument.
	 * 
	 * @param id
	 */
	public void setPackageId(Long id) {
		this.packageId = id;
	}

	public void setScopeType( String scopeType ) {
		this.scopeType = ScopeType.whichOne( scopeType );
	}
	/**
	 * Assign the microServices field with the provided argument.
	 * 
	 * @param microServices
	 */
	public void setMicroServices(boolean microServices) {
		this.microServices = microServices;
	}

	/**
	 * Assign the bundleApp field with the provided argument.
	 * 
	 * @param bundleApp
	 */
	public void setBundleApp(boolean bundleApp) {
		this.bundleApp = bundleApp;
	}

	public void setGitProject(boolean gitProject) {
		this.gitProject = gitProject;
	}

	/**
	 * Assign the gitProject field with the provided argument.
	 * 
	 * @param runApp
	 */
	public void setRunApp( boolean runApp ) {
		this.runApp = runApp;
	}

	/**
	 * Assign the overWrite field with the provided argument.
	 * 
	 * @param overWrite
	 */
	public void setOverWrite(boolean overWrite) {
		this.overWrite = overWrite;
	}

	/**
	 * Assign the workingDir field with the provided argument.
	 * 
	 * @param workingDir
	 */
	public void setWorkingDir(String workingDir) {
		if (workingDir != null && !workingDir.isEmpty())
			this.workingDir = workingDir;
	}

	/**
	 * Return the packageName field.
	 * 
	 * @return	String
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * Assign the packageName field with the provided argument.
	 * 
	 * @param packageName
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * Return the frameworkDescriptorName field.
	 * 
	 * @return String
	 */
	public String getFrameworkDescriptorName() {
		return frameworkDescriptorName;
	}

	/**
	 * Assign the frameworkDescriptorName field with the provided argument.
	 * 
	 * @param frameworkDescriptorName
	 */
	public void setFrameworkDescriptorName(String frameworkDescriptorName) {
		this.frameworkDescriptorName = frameworkDescriptorName;
	}

	/**
	 * Return the modelFileName field.
	 * 
	 * @return String
	 */
	public String getModelFileName() {
		return modelFileName;
	}

	/**
	 * Assign the modelFileName field with the provided argument.
	 * 
	 * @param modelFileName
	 */
	public void setModelFileName(String modelFileName) {
		this.modelFileName = modelFileName;
	}

	/**
	 * Return the modelId field.
	 * 
	 * @return Long
	 */
	public Long getModelId() {
		return modelId;
	}

	/**
	 * Assign the modelId field with the provided argument.
	 * 
	 * @param modelId
	 */
	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	/**
	 * Assign the projectId field with the provided argument.
	 * 
	 * @param projectId
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * Assign the persistAppFlag field with the provided argument.
	 * @param persistAppFlag
	 * 
	 */
	public void setPersistAppGen(boolean persistAppFlag) {
		this.persistAppGen = persistAppFlag;
	}

	/**
	 * Assign the nameToPersistApp field with the provided argument.
	 * 
	 * @param name
	 */
	public void setNameToPersistApp(String name) {
		this.nameToPersistApp = name;
	}

	/**
	 * Assign the appDescription field with the provided argument.
	 * 
	 * @param desc
	 */
	public void setDescription(String desc) {
		this.appDescription = desc;
	}

	/**
	 * Return the generateAppStats field.
	 * 
	 * @return String
	 */
	public GenerateAppStats getGenerateAppStats() {
		return generateAppStats;
	}

	/**
	 * Return the generatedAppDetails field.
	 * 
	 * @return List<GeneratedAppDetails>
	 */
	public List<GeneratedAppDetails> getGeneratedAppDetails() {
		return generatedAppDetails;
	}

	/**
	 * Return the primaryKeyType field.
	 * 
	 * @param primaryKeyType
	 */
	public void setPrimaryKeyType( String primaryKeyType ) {
		this.primaryKeyType = primaryKeyType;
	}
	
	/**
	 * Assign the options field with the provided argument.  
	 * 
	 * Delegates to AppGenHelper.dissectOptions
	 * 
	 * @param inputOptions
	 */
	public void setOptions(String inputOptions) {
		
		options = AppGenHelper.dissectOptions(inputOptions);
		
		final String msg = "input options dissected to " + options;
		
		LOGGER.info(msg);
	}

	/**
	 * Action handler to load all the previously saved application details
	 * 
	 * @return	String 
	 */
	public String loadGeneratedAppDetails() {
		try {
			generatedAppDetails = new GeneratedAppDetailsDAO().findAllGeneratedAppDetails();
		} catch (Exception exc) {
			final String msg = "Failed to load the saved generated app component details.";
			LOGGER.log( Level.SEVERE, msg, exc);
			addErrorMessage(msg);
		}

		return (SUCCESS);

	}

	/**
	 * Handles application generation, and optionally handles build the generated
	 * project
	 * 
	 * @return String
	 */
	public String exec() throws java.lang.InterruptedException {
		// assign stages
		initiateStages();

		modelFileName = (String) getTheSession().getAttribute( AppGenHelper.MODEL_NAME );

		if (modelFileName == null) {
			String errMsg = "Model cache expired. Model must be reloaded";
			LOGGER.severe( errMsg );
			addErrorMessage(errMsg);
			return (ERROR);
		}

		// ====================================================================
		// change the thread name to the model file name
		// ====================================================================
		Thread t = Thread.currentThread();
		t.setName(modelFileName);	
				
		// ====================================================================
		// can generate without a project being assigned, so be conditional
		// ====================================================================
		Project project = null;
		Build build 	= null;

		if ( projectId != null ) {
			try {
				project = new ProjectDAO().findProject( projectId );
			} catch( Exception exc ) {
				final String msg = "Unable to locate project using id " + projectId + ". Build tracking will not occur.";
				LOGGER.log( Level.WARNING, msg, exc);
			}
		}
		
		addInfoMessage(PREPARING_STAGE, "Preparing to generate app code...");
		Thread.sleep(2000);
		
		// ====================================================================
		// conditionally assign the primary key type if the client is choosing to 
		// override the internal default (Long)
		// ====================================================================
		if ( this.primaryKeyType != null && this.primaryKeyType.length() > 0 )
			com.realmethods.codetemplate.parser.ModelParser.modelParser().assignPrimaryKeyType( primaryKeyType );
		
		// ====================================================================
		// get the technology stack package to use
		// ====================================================================
		try {
			frameworkPackage = new FrameworkPackageDAO().findFrameworkPackage(packageId);
		} catch (Exception exc) {
			final String msg = "Unable to load the tech stack package";
			LOGGER.log( Level.WARNING, msg, exc);
			addErrorMessage(msg);
			return (ERROR);
		}

		// ====================================================================
		// create the object that encapsulates the notion of the application options
		// ====================================================================
		GenerateAppOptions appOptions 	= new GenerateAppOptions(options, this.determineOutputRootPath(), this.microServices);
		String workingDirPath 			= getWorkingDirPath(); 		// the absolute path of the working directory
		GenerateApp generateApp 		= null;
		String retVal					= SUCCESS;

		try {						
			// ====================================================================
			// if we are running with the context of project, create a build
			// ====================================================================
			if ( project != null )
				build = new Build(project);
			
			// ====================================================================
			// instantiate the object that does the application generation
			// ====================================================================
			generateApp = new GenerateApp( workingDirPath, 
											frameworkPackage, 
											appOptions,
											getUser());
			BuildApp buildApp 		= new BuildApp(appOptions);

			// ====================================================================
			// invocation to generate the application
			// ====================================================================
			generateTheApp( generateApp );
			
			// ====================================================================
			// invocation to build the generated application
			// ====================================================================
			String results = buildTheApp( generateApp, buildApp);
			
			if ( results.equalsIgnoreCase( SUCCESS ) ) {
				bundleTheApp( generateApp, appOptions );
				runTheApp( generateApp, buildApp );
			
				// ====================================================================
				// cache the generate app stats
				// ====================================================================
				this.generateAppStats = generateApp.getGenerateAppStats();
				getStatus().setSuccess( true );
				if ( build != null )
					build.setStatus( BuildStatus.SUCCESS );
			}
			else
				retVal = results;
			
			addInfoMessage("Done", "Application generation finished");
			
		} catch (Exception exc) {
			
			LOGGER.log(Level.WARNING, "App Creation", exc);			
			addErrorMessage("App creation exception: " + exc.getMessage());
			
			if( generateApp != null )
				generateApp.getGenerateAppStats().setGenerateStatus("failed");
			
			getStatus().setSuccess( false );

			if ( build != null )
				build.setStatus( BuildStatus.FAILED );
			
			retVal = ERROR;
		}
		finally {
			// ====================================================================
			// save both the build and the project conditionally
			// ====================================================================
			if ( project != null ) {
				try {
					// ====================================================================
					// mark the build as completed now
					// ====================================================================
					build.setEndDateTime( new Date() );	

					// ====================================================================
					// persist the build and add it to the current project
					// ====================================================================
					new BuildDAO().create(build);
					project.addBuild(build);

					// ====================================================================
					// save the project
					// ====================================================================
					new ProjectDAO().save(project);
				}
				catch( Exception daoExc ) {
					LOGGER.log( Level.SEVERE, "Project Save Failure", daoExc );
				}
			}

			// ====================================================================
			// request to clean up any work product on the instance
			// ====================================================================
			if ( generateApp != null )
				generateApp.cleanUp();
		
		}

		// ====================================================================
		// clean up any work product
		// ====================================================================
		cleanup();
		
		return (retVal);
	}

	/**
	 * Bundles the created application into a downloadable ZIP file and returns its name.
	 * 
	 * @param workingDir
	 * @return
	 */
	protected String bundleTheApp(String bundleDir) {
		// ====================================================================
		// ZIP the working directory entirely
		// ====================================================================
		String name = options.get("application.name").replaceAll( " ", "" );
		if ( name == null )
			name = this.nameToPersistApp;

		java.text.SimpleDateFormat sdf 	= new java.text.SimpleDateFormat(DATE_TIME_FORMAT);

		dateTimeArchived = sdf.format(new Date());
		
		String zipFileName 				= name + "_" + org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16) + ".zip";
		String zipFileAbsolute 			= getTheSession().getServletContext().getRealPath("/archive") 
												+ File.separator
												+ zipFileName;
		try {
			net.lingala.zip4j.core.ZipFile zipFile 			= new net.lingala.zip4j.core.ZipFile(zipFileAbsolute);
			net.lingala.zip4j.model.ZipParameters zipParams = new net.lingala.zip4j.model.ZipParameters();

			zipParams.setCompressionMethod(net.lingala.zip4j.util.Zip4jConstants.COMP_STORE);
			zipParams.setCompressionLevel(net.lingala.zip4j.util.Zip4jConstants.DEFLATE_LEVEL_NORMAL);

			zipFile.addFolder( bundleDir, zipParams );
						
			addInfoMessage("Successfully bundled application");

		} 
		catch (Exception exc) {
			LOGGER.log(Level.SEVERE, "General App Creation Error", exc);
			addErrorMessage("General error creating generated app archive " + zipFileAbsolute);
		}

		return (zipFileAbsolute);
	}

	
	/**
	 * Persist the application to the database
	 * 
	 * @param zipFileName
	 * @return
	 */
	protected GeneratedAppDetails persistApp(String zipFileName) {
		GeneratedAppDetails genAppDetails = null;
		
		try {
			String contentType = null; // use the default
			String s3Path = AWSHelper.self().copyFileToS3Bucket(zipFileName, 
												AWSHelper.self().buildUserPath(getUser()), contentType); 
			
			genAppDetails = new GeneratedAppDetails();
			genAppDetails.setName( (this.nameToPersistApp != null && !this.nameToPersistApp.isEmpty()) 
						? this.nameToPersistApp : this.options.get( "application.name"));
			genAppDetails.setDescription(appDescription);
			genAppDetails.setPackageId(this.frameworkPackage.getId().toString());
			genAppDetails.setModelId(this.modelId);
			genAppDetails.setFilePath(s3Path);
			genAppDetails.setScopeType(scopeType);
			genAppDetails.setOwnerId(getUser().getId());
			new GeneratedAppDetailsDAO().create(genAppDetails);
			addInfoMessage("Successfully persisted the bundled generated app");
		} catch (Exception exc) {
			addInfoMessage("Failed to persist the bundled generated app - " + exc.getMessage());
		}
		
		return genAppDetails;

	}

	/**
	 * Helper method to determine the actual root path of the output, depending
	 * on whether a relative or absolute path was provided, and if overwriting
	 * is allowed. If overwriting is not allowed, the directory name provided
	 * with be ended with a timestamp.
	 * 
	 * @return String path calculated
	 */
	protected String determineOutputRootPath() {
		if ( determinedOutputPath != null )
			return determinedOutputPath;
		
		String anchorRoot 		= "/" + APP_CREATION_OUTPUT_DIR;
		determinedOutputPath 	= getTheSession().getServletContext().getRealPath(anchorRoot);

		if (!overWrite) {
			// need to create a unique one
			String commonName = "app.build_" + this.frameworkPackage.getShortName();
			determinedOutputPath = determinedOutputPath + File.separator + commonName + "_" + org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(8);
		} 

		final String msg = "outputRootPath is " + determinedOutputPath;
		LOGGER.info(msg);
		return (determinedOutputPath);
	}

	/**
	 * The root directory where working is output to
	 * 
	 * @return String
	 */
	protected String getWorkingDirPath() {
		return (getTheSession().getServletContext().getRealPath("/" + WORKING_DIR));
	}

	/**
	 * Assigns the options field with the provided argument.
	 * 
	 * @param options
	 */
	protected void assignOptionsInternally( Map<String, String> options ) {
		this.options = options;
	}
	
	/**
	 * seeds the stages for generation
	 */
	protected void initiateStages()
	{
		ArrayList stages = new ArrayList();
		stages.add( PREPARING_STAGE );
		stages.add( GENERATING_STAGE );
		
//		if ( this.gitProject )
			stages.add( BUILDING_STAGE );

		if ( this.bundleApp )
			stages.add( ARCHIVING_STAGE );
		
		if (this.persistAppGen )
			stages.add( SAVING_STAGE );
		
		if (this.runApp)
			stages.add( RUNNING_STAGE );

		stages.add( DONE_STAGE );
		assignStages( stages );
	}

	protected void generateTheApp( GenerateApp generateApp ) throws GenerationException {
		addInfoMessage(GENERATING_STAGE, "App generation in progress...");
		pauseForABit(2000);
		
		// before generating, if gitParams have been provided, place them as app options
		// replacing  the ones that are in the options Map
		if ( this.gitParams != null ) {
			options.putAll( gitParams.asMap() );
		}
		
		// delegate to the true generation handler
		generateApp.generate();

		addInfoMessage(GENERATING_STAGE, "App generation successful");

		generateApp.getGenerateAppStats().setGenerateStatus("successful");		
	}
	
	protected void bundleTheApp(GenerateApp generateApp, GenerateAppOptions appOptions  ) {
		if ( this.bundleApp ) {
			LOGGER.info("Bundling the app --");
			addInfoMessage(ARCHIVING_STAGE, "Application archiving starting...");
			
			pauseForABit(2000);
			
			String zipFileFullPath = bundleTheApp(appOptions.getWorkingDir());
			
			generateApp.getGenerateAppStats().setResultLocation( zipFileFullPath );
			generateApp.getGenerateAppStats().setResultPath( appOptions.getWorkingDir() );
		
			LOGGER.info("Done bundling the app --");
			addInfoMessage(ARCHIVING_STAGE, "Application archiving finished");
			
			// if persist app flag enabled, save it
			if (this.persistAppGen) {
				LOGGER.info("Saving the app --");
				addInfoMessage(SAVING_STAGE, "Saving the application...");

				pauseForABit(2000);
				
				persistApp(zipFileFullPath);
				LOGGER.info("Done persisting the app --");
				addInfoMessage(SAVING_STAGE, "Application saving finished");
			}
		}
	}

	private void cleanup() {
		
		// keep app files only if in QA stage
		if ( !FrameworkNameSpace.IN_QA ) {
			String wrkDir = determineOutputRootPath();
			if ( org.apache.commons.io.FileUtils.deleteQuietly(new java.io.File(wrkDir)) )
				LOGGER.log(Level.INFO,  "Successfully cleaned up working directory {0} ", wrkDir );
			else 
				LOGGER.log(Level.INFO,  "Failed to clean up working directory {0}", wrkDir );
		}		
	}
	
	protected String buildTheApp(GenerateApp generateApp, BuildApp buildApp ) {

		String result 			= SUCCESS;
		boolean buildResults 	= false;
		
		LOGGER.info("Build App --");
		addInfoMessage(BUILDING_STAGE, "Building starting...");
		
		pauseForABit(2000);

		// execute a project compilation which is likely a Maven query to compile and format the files
		// when possible.  the creation of relevant CI/CD Yaml files makes building and testing better
		// served off of the realMethods infrastructure onto the CI/CD platform
		buildResults = buildApp.compile();
			
		if ( !buildResults ) { 
			addErrorMessage(BUILDING_STAGE, "Deploy Error Output: " + buildApp.getExecutionStatus() );
			result = ERROR;
		} else {
			addInfoMessage(BUILDING_STAGE, "Deploy Success: " + buildApp.getExecutionStatus() );				
		}
		
		generateApp.getGenerateAppStats().setBuildStatus(buildApp.getExecutionStatus() );

		LOGGER.info("Done building--");
		addInfoMessage(BUILDING_STAGE, "Building finished");
		
		if ( !buildApp.getErrorOutput().isEmpty() )
			result = buildApp.getErrorOutput();
			
		return result;
		
	}

	protected void runTheApp( GenerateApp generateApp, BuildApp buildApp ) {

		if (this.runApp) {
			LOGGER.info("Running App --");
			addInfoMessage(RUNNING_STAGE, "Running the application");
			pauseForABit(2000);
			if( !buildApp.run() ) {
				LOGGER.info("Running the app failed");
				addInfoMessage(RUNNING_STAGE, "Application run finished with failure");
			}
			else {
				LOGGER.info("Done running the app --");
				addInfoMessage(RUNNING_STAGE, "Application run finished successfully");
			}
			
			if (generateApp != null)
				generateApp.getGenerateAppStats().setExecuteStatus(buildApp.getExecutionStatus());
		}				
	}
	
	protected void pauseForABit(int millisecondsToPause ) {
		try {
			Thread.sleep(millisecondsToPause);
		} catch( Exception exc ){ 
			//no_op
		}
	}
	
	// attributes
	protected Long packageId 					= null;
	protected String packageName 				= null;
	protected String frameworkDescriptorName 	= null;
	protected String modelFileName 				= null;
	protected String workingDir 				= null;
	protected String packageUnzipPath			= null;
	protected String appDescription 			= null;
	protected String primaryKeyType 			= null;
	protected String determinedOutputPath 		= null;
	protected String dateTimeArchived 			= null;
	protected String nameToPersistApp 			= "rm_app_";	
	protected Long modelId 						= null;
	protected Long projectId					= null;
	protected boolean overWrite 				= true;
	protected boolean bundleApp 				= false;
	protected boolean gitProject 				= false;
	protected boolean runApp 					= false;
	protected boolean microServices 			= false;
	protected boolean persistAppGen 			= false;
	protected ScopeType scopeType				= ScopeType.getDefaultValue();
	protected transient Map<String, String> options = null;
	private transient GenerateAppStats generateAppStats 	= null;
	private transient GitParams gitParams					= null;
	private transient List<GeneratedAppDetails> generatedAppDetails = null;
	private transient FrameworkPackage frameworkPackage = null;
	
	private static final String PREPARING_STAGE = "Preparing";
	private static final String GENERATING_STAGE= "Generating";
	private static final String BUILDING_STAGE 	= "Building";
	private static final String RUNNING_STAGE 	= "Running";
	private static final String SAVING_STAGE 	= "Saving";
	private static final String ARCHIVING_STAGE = "Archiving";
	private static final String DONE_STAGE 		= "Done";
	
	protected static final String DATE_TIME_FORMAT 	= "MM-dd-yyyy_HH.mm.ss";
	protected static final String WORKING_DIR 		= "tech.stack.packages";
	private static final String APP_CREATION_OUTPUT_DIR = "app.creation.output";
	private static final Logger LOGGER 				= Logger.getLogger(GenerateAppAction.class.getName());
}
