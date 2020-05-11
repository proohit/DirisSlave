mkdir -p "build/install/DirisSlave/bin/config"
cd build/install/DirisSlave/bin/config
touch config.properties
true > config.properties

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
    *)    # unknown option
    POSITIONAL+=("$1") # save it in an array for later
    shift # past argument
    ;;
esac
done
set -- "${POSITIONAL[@]}" # restore positional parameters

echo JDAToken=$JDAToken >> config.properties
echo SpotifyClientId=$SpotifyClientId >> config.properties
echo SpotifyClientSecret=$SpotifyClientSecret >> config.properties