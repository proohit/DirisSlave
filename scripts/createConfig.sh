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
    --owmappid)
    OWMAppid="$2"
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