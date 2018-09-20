# Request corporation tax number stub

CT UTR Stub. 

## Info

This service is also known as Ask for a copy of your Corporation Tax UTR

## Running the service

Service Manager: CTUTR_ALL 

|Repositories|Link|
|------------|----|
|Frontend|https://github.com/hmrc/request-corporation-tax-number-frontend|
|Backend|https://github.com/hmrc/request-corporation-tax-number|
|Performance tests|https://github.com/hmrc/request-corporation-tax-number-performance-test|

Routes
-------
Port: 9203

### Stubbed file upload
| *Url* | *Description* |
|-------|---------------|
| /file-upload/envelopes | Creates envelope |
| /file-upload/upload/envelopes/:envelopeId/files/:fileId | Upload file to specific envelope |
| /file-upload/envelopes/:envelopeId | Summary of the envelope |
| /file-routing/requests | Closes envelope |

### Stubbed pdf generator
| *Url* | *Description* |
|-------|---------------|
| /pdf-generator-service/generate | Generates a PDF |
