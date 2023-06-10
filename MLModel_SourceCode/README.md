# Keyword Detection 

- Data Generation Using SpeechT5 model and voxpopuli dataset - KeywordDetectionDataGenerationSpeechT5.ipynb
- Keyword Detection CNN model code from Edge Impulse: KeywordDetectionCNNModel.py

# Panic Detection 
### RADVESS data pre-processing:
- Step1: Extract label from file name and convert to Numpy array - ProjectDetectorDataPreprocessing-1.ipynb
- Step2: Separate data into Training and test folders with only 2 labels - 'fear' and 'rest'. - PanicDetectionDataPreprocessing-2.ipynb

#### Model Training
- Basic CNN - PanicDetectionCNN.py
- Transformer Encoder - PanicDetectionTransformerModel.py

# References
1. voxpopuli: https://huggingface.co/datasets/facebook/voxpopuli 
2. RAVDESS: https://zenodo.org/record/1188976
3. SpeechT5: https://github.com/microsoft/SpeechT5
4. Vocoder (SpeechT5HifiGan): https://huggingface.co/microsoft/speecht5_hifigan
5. Speaker Embedding - https://huggingface.co/speechbrain/spkrec-xvect-voxceleb 
