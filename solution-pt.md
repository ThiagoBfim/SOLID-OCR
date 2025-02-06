## Atividades

1. Criar um endpoint para receber uma imagem e extrair o texto dela, OCR (Optical Character Recognition).

Solution:

![1 SRP.png](images/1_SRP.png)

2. Queremos evoluir para suportar o serviço do Google e da Cloudinary.
   Caso o serviço do Cloudinary esteja indisponível a chamada deverá ser feita para o do Google.

Solution:

![3 OCP.png](images/3_OCP.png)

3. Queremos evoluir para armazenar as cotas, e caso exceda as cotas de um provedor, ele deverá buscar no outro provedor.

Solution:

![5_LSP.png](images/5_LSP.png)

4. Estamos recebendo muitas requisições, queremos armazenar o texto gerado em Banco de dados.

Solution:

![6_NOT_ISP.png](images/6_NOT_ISP.png)