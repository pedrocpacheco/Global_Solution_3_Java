# Requisições API

```http
POST http://localhost:8080/autoridades
Content-Type: application/json

{
    "nome": "Autoridade Exemplo",
    "email": "autoridade@example.com",
    "senha": "senha123",
    "departamento": "Departamento Exemplo",
    "descricao": "Descrição da Autoridade"
}

PUT http://localhost:8080/autoridades/1
Content-Type: application/json

{
    "nome": "Autoridade Exemplo Atualizada",
    "email": "autoridade_atualizada@example.com",
    "senha": "novaSenha456",
    "departamento": "Novo Departamento Exemplo",
    "descricao": "Nova Descrição da Autoridade"
}

POST http://localhost:8080/sensores
Content-Type: application/json

{
    "id": 1,
    "modelo": "Modelo X",
    "marca": "Marca Y",
    "tipoSensor": "TIPO_A"
}

PUT http://localhost:8080/sensores/1
Content-Type: application/json

{
    "modelo": "Novo Modelo Sensor",
    "marca": "Nova Marca Sensor",
    "tipoSensor": "TIPO_B"
}

POST http://localhost:8080/rotas
Content-Type: application/json

{
    "id": 1,
    "nome": "Rota Exemplo",
    "origem": "Origem Exemplo",
    "destino": "Destino Exemplo"
}

PUT http://localhost:8080/rotas/1
Content-Type: application/json

{
    "nome": "Rota Exemplo Atualizada",
    "origem": "Nova Origem Exemplo",
    "destino": "Novo Destino Exemplo"
}

POST http://localhost:8080/notificacoes
Content-Type: application/json

{
    "mensagem": "Nova notificação de teste",
    "autoridade": {
        "id": 1
    }
}

PUT http://localhost:8080/notificacoes/1
Content-Type: application/json

{
    "mensagem": "Notificação atualizada",
    "autoridade": {
        "id": 1
    }
}

POST http://localhost:8080/leituras
Content-Type: application/json

{
    "valor": "50",
    "rota": {
        "id": 1
    },
    "sensor": {
        "id": 1
    },
    "notificacao": {
        "id": 1
    }
}

PUT http://localhost:8080/leituras/1
Content-Type: application/json

{
    "valor": "75",
    "rota": {
        "id": 1
    },
    "sensor": {
        "id": 1
    },
    "notificacao": {
        "id": 1
    }
}

POST http://localhost:8080/navios
Content-Type: application/json

{
    "nome": "Navio Teste",
    "marca": "Marca Teste",
    "modelo": "Modelo Teste",
    "tipoNavio": "PETROLEIROS"
}

PUT http://localhost:8080/navios/1
Content-Type: application/json

{
    "nome": "Navio Teste Atualizado",
    "marca": "Nova Marca Teste",
    "modelo": "Novo Modelo Teste",
    "tipoNavio": "CARGA_GERAL"
}
