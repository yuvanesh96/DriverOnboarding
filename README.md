# Driver Onboarding Module

### This repo contains the demo code that exposes apis required for Driver registration and Onboarding.

### Data Store
* Currently we use InMemory Data store for this demo.

### Rest Apis exposed

- Post : "/v1/register/driver" -> Used by driver to register the profile details.
- Post : "/v1/update/docs-verified" -> Used by third-party-screener to update the verfication result of the document for a particular user.
- Post : "/v1/update/ready" -> Used by driver to update the readiness of the driver to take up rides.
- Get  : "/v1/register/documents" -> Used to get the documents that are required for verification for a particular driver.
- POSt : "/v1/register/cab" -> Used by driver to register his cab.


Happy Coding !
