# Prova Técnica - Stoom Store

Repositório da aplicação RESTful da prova técnica [(Teste Stoom)](https://github.com/Luccasplima/teste-stoom) 
que oferece funcionalidades para gerenciar **Produtos**, **Marcas** e **Categorias**. Foi desenvolvido utilizando o **Spring Boot** e segue as boas práticas de desenvolvimento com **100% de cobertura nos testes unitários**.

---

## 🔧 Funcionalidades

- **CRUD de Produtos**: Criação, leitura, atualização e exclusão.
- **CRUD de Marcas**: Criação, leitura, atualização e exclusão.
- **CRUD de Categorias**: Criação, leitura, atualização e exclusão.
- **Swagger**: Documentação interativa da API. Disponível em: http://localhost:8080/swagger-ui/index.html#
- **Testes unitários**: Cobertura completa das funcionalidades.

--- 

## Endpoints da API

### Produtos
| **Método** | **Endpoint**          | **Descrição**                       |
|------------|------------------------|-------------------------------------|
| GET        | `/api/product/`        | Lista todos os produtos.            |
| POST       | `/api/product/`        | Cria um novo produto.               |
| PUT        | `/api/product/{id}`    | Atualiza um produto.                |
| DELETE     | `/api/product/{id}`    | Remove um produto.                  |
| PATCH      | `/api/product/status/{id}`    | Atualiza o status de um produto.    |

### Marcas
| **Método** | **Endpoint**          | **Descrição**                       |
|------------|------------------------|-------------------------------------|
| GET        | `/api/brand/`          | Lista todas as marcas.              |
| POST       | `/api/brand/`          | Cria uma nova marca.                |
| PUT        | `/api/brand/{id}`      | Atualiza uma marca.                 |
| DELETE     | `/api/brand/{id}`      | Remove uma marca.                   |
| PATCH      | `/api/brand/status/{id}`    | Atualiza o status de uma marca.    |

### Categorias
| **Método** | **Endpoint**          | **Descrição**                       |
|------------|------------------------|-------------------------------------|
| GET        | `/api/category/`       | Lista todas as categorias.          |
| POST       | `/api/category/`       | Cria uma nova categoria.            |
| PUT        | `/api/category/{id}`   | Atualiza uma categoria.             |
| DELETE     | `/api/category/{id}`   | Remove uma categoria.               |
| PATCH      | `/api/category/status/{id}`    | Atualiza o status de uma categoria.    |

---

## Testes Unitários

Cobertura de 100% dos testes unitários

![image](https://github.com/user-attachments/assets/ea4ca6ea-af20-4ec5-95d1-38f1d321b8aa)

![image](https://github.com/user-attachments/assets/62a5d6ee-cf54-4f81-a50c-fc64f0260439)

## Observações

- Pensei em dockerizar a aplicação, porém proposto não alterar a execução da aplicação.
-  O SKU é gerado no formato `AAA-BBB-CCC-TIMESTAMP`, onde:  
  - `AAA` = 3 primeiras letras do nome da categoria.  
  - `BBB` = 3 primeiras letras do nome da marca.  
  - `CCC` = 3 primeiras letras do nome do produto.  
  - `TIMESTAMP` = marca de tempo única para garantir unicidade.   

## Feedback 

Gostaria de agradecer pela oportunidade de participar deste processo seletivo. Espero poder contribuir com minhas habilidades e fazer parte do sucesso da equipe.
