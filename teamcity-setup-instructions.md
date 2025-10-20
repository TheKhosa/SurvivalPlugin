# TeamCity Setup Instructions for SurvivalPlugin

## Manual Setup (Web UI)

Since the TeamCity server is in maintenance mode, here are the manual setup steps:

### 1. Create Project
1. Go to http://145.239.253.161:8111/
2. Click "Create Project"
3. Choose "From a repository URL"
4. Enter: `https://github.com/TheKhosa/SurvivalPlugin.git`
5. Project Name: `SurvivalPlugin`
6. Click "Proceed"

### 2. Configure Build Steps
1. Go to Build Configuration → Build Steps
2. Add Maven build step:
   - Goals: `clean package`
   - Additional Maven command-line parameters: `-DskipTests`
   - JDK: Java 17 or higher

### 3. Configure Artifacts
1. Go to Build Configuration → General Settings
2. Artifact paths: `target/*.jar`

### 4. Configure VCS Trigger
1. Go to Build Configuration → Triggers
2. Add VCS Trigger to build on every commit

### 5. Deploy to Server (Optional)
To automatically deploy to `D:\MCServer\Survival\plugins`:
1. Add a Command Line build step after Maven:
2. Script: `copy target\SurvivalPlugin-*.jar D:\MCServer\Survival\plugins\`

## API Setup (When Maintenance Mode is Off)

Use these curl commands:

```bash
# Create VCS Root
curl -u "AIAgent:hX%5*89" -H "Content-Type: application/xml" -X POST \
  http://145.239.253.161:8111/app/rest/vcs-roots \
  -d '<vcs-root name="SurvivalPlugin Git" vcsName="jetbrains.git">
    <project id="_Root"/>
    <properties>
      <property name="url" value="https://github.com/TheKhosa/SurvivalPlugin.git"/>
      <property name="branch" value="refs/heads/main"/>
      <property name="authMethod" value="ANONYMOUS"/>
    </properties>
  </vcs-root>'

# Create Project
curl -u "AIAgent:hX%5*89" -H "Content-Type: application/xml" -X POST \
  http://145.239.253.161:8111/app/rest/projects \
  -d '<project><name>SurvivalPlugin</name></project>'

# Create Build Configuration
curl -u "AIAgent:hX%5*89" -H "Content-Type: application/xml" -X POST \
  http://145.239.253.161:8111/app/rest/buildTypes \
  -d '<buildType name="Build" projectId="SurvivalPlugin">
    <settings>
      <property name="artifactRules" value="target/*.jar"/>
    </settings>
  </buildType>'
```

## Kotlin DSL (Versioned Settings)

The `.teamcity/settings.kts` file has been created in the repository for versioned settings.
TeamCity can automatically import this configuration.
