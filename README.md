# DirisSlave

## Build

Run

```shell script
gradlew.bat clean installDist
```

or

```shell script
gradlew clean installDist
```

to build the Bot.

## Run

To run the bot, use gradle:

```shell script
gradlew.bat run
```

in Windows or

```shell script
gradle run
```

to run in Shell/Bash. Expects a `config.properties` File in `./config` folder

## Configuration

The Bot searches for a config file named `config.properties` in a root folder `config`

It is mandatory to provide a property `JDAToken` which can be retrieved from <https://discordapp.com/developers/> in the Application/Bot section

To easily create such a config file, there is a script [createConfig.sh](./scripts/createConfig.sh) in scripts.

createConfig.sh

```shell script
createConfig.sh
    --jdatoken <JDA Token>
    --dbHost <database host with port>
    --dbPassword <database password>
    --dbDatabase <database name>
    --dbUser <database username>
    [--spotifyClientId <clientId>]
    [--spotifyClientSecret <clientSecret>]
    [--owmappid <OpenWeatherMapAppId>]
    [--pixabaykey <PixabayKey>]
    -o <output directory>
```
