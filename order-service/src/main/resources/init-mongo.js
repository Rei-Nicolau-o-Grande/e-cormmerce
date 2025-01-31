// Troca para o banco de dados
db = db.getSiblingDB('order_db');

// Cria uma coleção chamada
db.createCollection('orders');