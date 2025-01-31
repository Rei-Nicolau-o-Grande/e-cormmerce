// Troca para o banco de dados
db = db.getSiblingDB('notification_db');

// Cria uma coleção chamada
db.createCollection('notifications');