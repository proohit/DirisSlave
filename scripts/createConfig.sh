#!/bin/bash
POSITIONAL=()
while [[ $# -gt 0 ]]
do
key="$1"
case $key in
    --jdatoken)
    JDAToken="$2"
    shift # past argument
    shift # past value
    ;;
    --dbPassword)
    dbPassword="$2"
    shift # past argument
    shift # past value
    ;;
    --dbHost)
    dbHost="$2"
    shift # past argument
    shift # past value
    ;;
    --dbDatabase)
    dbDatabase="$2"
    shift # past argument
    shift # past value
    ;;
     --dbUser)
    dbUser="$2"
    shift # past argument
    shift # past value
    ;;
    --spotifyClientId)
    SpotifyClientId="$2"
    shift # past argument
    shift # past value
    ;;
    --spotifyClientSecret)
    SpotifyClientSecret="$2"
    shift # past argument
    shift # past value
    ;;
    --owmappid)
    OWMAppid="$2"
    shift # past argument
    shift # past value
    ;;
    --pixabaykey)
    PixabayKey="$2"
    shift # past argument
    shift # past value
    ;;
    -o)
    output="$2"
    shift # past argument
    shift # past value
    ;;
    *)    # unknown option
    POSITIONAL+=("$1") # save it in an array for later
    shift # past argument
    ;;
esac
done
set -- "${POSITIONAL[@]}" # restore positional parameters

mkdir -p "$output/config"
cd $output/config
touch config.properties
true > config.properties

echo JDAToken=$JDAToken >> config.properties
echo SpotifyClientId=$SpotifyClientId >> config.properties
echo SpotifyClientSecret=$SpotifyClientSecret >> config.properties
echo OWMAppid=$OWMAppid >> config.properties
echo PixabayKey=$PixabayKey >> config.properties
echo dbPassword=$dbPassword >> config.properties
echo dbHost=$dbHost >> config.properties
echo dbDatabase=$dbDatabase >> config.properties
echo dbUser=$dbUser >> config.properties

