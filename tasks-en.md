## Tasks

1. Create an endpoint to receive an image and extract text from it using OCR (Optical Character Recognition).

2. We want to evolve to support both Google and Cloudinary services.
   If the Cloudinary service is unavailable, the request should be made to Google.

3. We want to evolve to store quotas, and if a provider's quota is exceeded, it should retrieve from another provider.

4. We are receiving many requests and want to store the generated text in a database.
