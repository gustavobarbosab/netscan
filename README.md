# ![netscan@2x](https://github.com/gustavobarbosab/netscan/assets/11272342/7878c286-759f-40f6-8b4d-ddd1f48e4954)


![GitHub repo size](https://img.shields.io/github/repo-size/gustavobarbosab/netscan?style=for-the-badgee)
![GitHub language count](https://img.shields.io/github/languages/count/gustavobarbosab/netscan?style=for-the-badgee)
![GitHub forks](https://img.shields.io/github/forks/gustavobarbosab/netscan?style=for-the-badgee)
![GitHub open issues](https://img.shields.io/bitbucket/issues/gustavobarbosab/netscan?style=for-the-badgee)
![GitHub open pull requests](https://img.shields.io/bitbucket/pr-raw/gustavobarbosab/netscan?style=for-the-badgee)

## Última versão estável: ![Última versão](https://img.shields.io/github/v/release/gustavobarbosab/netscan?include_prereleases&style=flat-square)

## O que é a NetScan?

O objetivo deste trabalho é a criação de um conjunto abrangente de ferramentas de rede que sejam regularmente atualizadas, incorporando os mais recentes padrões erecursos do Android. Dentro desta biblioteca intitulada NetScan, encontra-se o seguinte conjunto de ferramentas:

- Escaneamento de portas
- Pesquisa por dispositivo na rede (Ping).
- Varredura em busca de dispositivos em redes domésticas.
- Verificação de conexão com a Internet.
- Lista de redes sem fio próximas.
- Velocidade da conexão com a Internet.

## ❤️ Como utilizar a NetScan?

### Primeira etapa:
Para utilizar a biblioteca, inclua a URL do repositório JitPack no seu arquivo `settings.gradle`, conforme o exemplo abaixo:
```
dependencyResolutionManagement {
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Caso a abordagem acima não funcione, ou o projeto possua outro modelo de configuração, atualize o arquivo `build.gradle` da seguinte forma:
```
allprojects {
    repositories {
	  ...
	  maven { url 'https://jitpack.io' }
    }
}
```

### Segunda etapa:

Inclua a dependência da biblioteca da biblioteca no arquivo `build.gradle` do projeto.

```
dependencies {
    implementation `com.github.gustavobarbosab:netscan:VERSAO'
}
```

## Seja um dos contribuidores<br>

Quer fazer parte desse projeto? Abra uma PR com sua contribuição.

## 📝 Licença

Esse projeto está sob licença. Veja o arquivo [LICENÇA](LICENSE.md) para mais detalhes.





