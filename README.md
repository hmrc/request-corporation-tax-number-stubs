# request-corporation-tax number-stubs

Stub for CT UTR Service. 

This service is also known as Ask for a copy of your Corporation Tax UTR

|Repositories|Link|
|------------|----|
|Frontend|https://github.com/hmrc/request-corporation-tax-number-frontend|
|Backend|https://github.com/hmrc/request-corporation-tax-number|
|Acceptance tests|https://github.com/hmrc/request-corporation-tax-number-journey-tests|
|Performance tests|https://github.com/hmrc/request-corporation-tax-number-performance-test|

---

## Running the service

You will need to have the following:

* Installed/configured [service manager 2](https://github.com/hmrc/sm2)
* Installed [MongoDB](https://www.mongodb.com/docs/manual/installation/)

The service manager profile for this service is: `REQUEST_CORPORATION_TAX_NUMBER_STUBS`

Use the following to run all the microservices and dependant services used for CT UTR:

`sm2 --start CTUTR_ALL`

If you want to run your local copy, then stop running the stub via the service manager and run your local code by using the following:

```
sm2 --start CTUTR_ALL
sm2 --stop REQUEST_CORPORATION_TAX_NUMBER_STUBS

cd request-corporation-tax-number-stubs
sbt run
```

### Routes

Port: `9203`

#### Stubbed file upload

| Url | Description |
|-----|-------------|
| /file-upload/envelopes | Creates envelope |
| /file-upload/upload/envelopes/:envelopeId/files/:fileId | Upload file to specific envelope |
| /file-upload/envelopes/:envelopeId | Summary of the envelope |
| /file-routing/requests | Closes envelope |


#### Stubbed pdf generator

| Url | Description |
|-----|-------------|
| /pdf-generator-service/generate | Generates a PDF |

---

## Testing the service

This service uses [sbt-scoverage](https://github.com/scoverage/sbt-scoverage) to provide test coverage reports.


Run this script before raising a PR to ensure your code changes pass the Jenkins pipeline. This runs all the unit tests and checks for dependency updates:

```
./run_all_tests.sh
```

---

## License

This code is open source software licensed under the Apache 2.0 License.
