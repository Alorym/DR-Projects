import freesound, sys,os
import matplotlib.pyplot as plt
import librosa
import librosa.display
import numpy as np

client = freesound.FreesoundClient()
client.set_token("vbiuKlzCwyzvrLvMr95rFBnQ7jNkiB","oauth")

def display(filename):
    y, sr = librosa.load(filename)
    S = librosa.feature.melspectrogram(y=y, sr=sr, n_mels=128, fmax=8000)
    S_dB = librosa.power_to_db(S, ref=np.max)
    plt.figure()
    plt.subplot(3, 1, 1)
    librosa.display.waveplot(y, sr=sr)
    plt.subplot(3, 1, 2)
    librosa.display.specshow(S_dB, x_axis='time', y_axis='mel', sr=sr, fmax=8000)
    plt.colorbar(format='%+2.0f dB')
    plt.title('Mel-frequency spectrogram')
    plt.show()


def download_sounds(query, fields="id,name,previews,type", limit=10):
    count = 0
    sounds_results_pager = client.text_search(query=query, fields=fields)
    path = "./sounds/"
    subpath = '_'.join(query.split(' '))
    path_name = path + subpath + "/"
    if not os.path.exists(path_name):
        os.makedirs(path_name)
    while True:
        for sound in sounds_results_pager:
            print("\t\tDownloading:", sound.name)

            # Some sound filenames already end with the type...
            if sound.name.endswith(sound.type):
                filename = sound.name
            else:
                filename = "%s.%s" % (sound.name, sound.type)

            sound.retrieve(path_name, name=filename)
            y, sr = librosa.load(path_name + "/" + filename)
            onset_frames = librosa.onset.onset_detect(y=y,sr=sr)
            if (len(onset_frames) > 1):
                print("%i onsets detected, deleteing file" % len(onset_frames))
                os.remove(path_name + "/" + filename)
            else: 
                count += 1

            if count >= limit:
                break

        if count >= limit:
            break
        if not sounds_results_pager.next:
            break
        sounds_results_pager = sounds_results_pager.next_page()

to_download = ["tom", "hi-hat (open)", "hi-hat (closed)"]

#for item in to_download:
  #download_sounds(item)

#to_display = [ "./sounds/Tom/%s" % filename for filename in os.listdir("./sounds/Tom")]

count = 0
limit = 10
for item in to_download:
    download_sounds(item)
#for item in to_display:
#   display(item)
#    count += 1
#    if count > limit:
#        break
