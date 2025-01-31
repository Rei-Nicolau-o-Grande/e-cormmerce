import os
import threading

# Define a ordem dos serviços
SERVICES_ORDER = [
    'service-discovery',
    'api-gateway',
    'product-service',
    'order-service',
    'notification-service',
]

# Função para parar e remover todos os containers
def remove_remaining_containers():
    print("Parando e removendo todos os containers...")
    os.system("docker stop $(docker ps -a -q)")  # Parando todos os containers
    os.system("docker rm $(docker ps -a -q)")    # Remove todos os containers parados
    os.system("docker network prune -f")         # Remove todas as networks não utilizadas
    os.system("docker volume prune -f")          # Remove todos os volumes não utilizados
    print("Todos os containers foram parados e removidos.")

def clean_docker_system():
    print("Removendo imagens Docker não utilizadas...")
    os.system("docker system prune -af --volumes")  # Remove tudo que não está em uso
    print("Imagens Docker não utilizadas removidas.")

def docker_compose_down():
    print("Parando o Docker Compose...")
    os.system("docker compose down -v")
    print("Docker Compose foi parado.")

# Função para subir o Docker Compose
def docker_compose_up():
    print("Iniciando o Docker Compose...")
    os.system("docker compose up --build -d")
    print("Docker Compose iniciado.")

# Função para construir a aplicação
def build_application(app_name):
    print(f"Construindo a aplicação {app_name}...")
    os.system(f"cd {app_name} && mvn clean package")
    print(f"Aplicação {app_name} construída com sucesso!")

# Função para construir todas as aplicações
def build_all_applications():
    print("Construindo todas as aplicações na ordem...")
    threads = []
    for service in SERVICES_ORDER:
        thread = threading.Thread(target=build_application, args=(service,))
        thread.start()
        threads.append(thread)

    # Aguarda a conclusão de todas as threads
    for thread in threads:
        thread.join()

    print("Todas as aplicações foram construídas com sucesso.")

def delete_mongo_data():
    print("Deletando dados do .e-cormmerce...")
    os.system("sudo chmod -R 777 .e-cormmerce")
    os.system("rm -rf .e-cormmerce")
    print("Dados do Escola deletados.")

def ask_confirmation(prompt):
    """Solicita confirmação do usuário para continuar com uma etapa."""
    while True:
        user_input = input(f"{prompt} (S/n): ").strip().lower()
        if user_input == '' or user_input == 's':
            return True
        elif user_input == 'n':
            return False
        else:
            print("Entrada inválida. Por favor, digite 'S' ou 'n'.")

def run_full_pipeline():
    """Executa todas as etapas do pipeline de uma vez se o usuário confirmar."""
    if ask_confirmation("Você quer rodar o pipeline completo (docker-compose down, limpar sistema, construir apps, docker-compose up)?"):
        # Remova containers antigos
        print("Parando e removendo containers...")
        docker_compose_down()

        # Limpeza do sistema Docker
        print("Limpando o sistema Docker...")
        clean_docker_system()

        # Deletar dados do MongoDB
        print("Deletando dados do Escola...")
        delete_mongo_data()

        # Build todas as aplicações
        print("Construindo todas as aplicações...")
        build_all_applications()

        # Subindo todos os containers
        print("Iniciando o Docker Compose...")
        docker_compose_up()

        print("Pipeline completo executado com sucesso!")
        return True  # Retorna True para indicar que o pipeline completo foi rodado
    return False  # Retorna False se o usuário não optar por rodar o pipeline completo

if __name__ == "__main__":
    print("Pipeline iniciado.")
    try:
        # Pergunta se deseja rodar o pipeline completo
        if run_full_pipeline():
            # Se o pipeline completo foi rodado, não faz mais perguntas
            print("Etapas do pipeline concluídas sem mais perguntas.")
        else:
            # Se o usuário não escolheu rodar o pipeline completo, faz as perguntas restantes
            if ask_confirmation("Você quer parar e remover todos os containers?"):
                docker_compose_down()

            if ask_confirmation("Você quer remover imagens, containers e volumes Docker não utilizados?"):
                clean_docker_system()

            if ask_confirmation("Você quer deletar os dados do Escola?"):
                delete_mongo_data()

            if ask_confirmation("Você quer construir todas as aplicações?"):
                build_all_applications()

            if ask_confirmation("Você quer iniciar todos os containers Docker?"):
                docker_compose_up()

            print("Pipeline finalizado com sucesso!")
    except Exception as e:
        print(f"Ocorreu um erro: {e}")