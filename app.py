from flask import Flask, request, jsonify
import numpy as np
import pickle
model = pickle.load(open('model.pkl', 'rb'))
app = Flask(__name__)
@app.route('/')
def index():
    return "Hello world"
@app.route('/predict', methods=['POST'])
def predict():
    clump_thick = request.form.get('clump_thickness')
    size_uniformity = request.form.get('size_uniformity')
    shape_uniformity = request.form.get('shape_uniformity')
    marginal_adhesion = request.form.get('marginal_adhesion')
    epithelial_size = request.form.get('epithelial_size')
    bare_nucleoli = request.form.get('bare_nucleoli')
    bland_chromatin = request.form.get('bland_chromatin')
    normal_nucleoli = request.form.get('normal_nucleoli')
    mitoses = request.form.get('mitoses')
    input_query = np.array([[clump_thick, size_uniformity, shape_uniformity, marginal_adhesion,
                             epithelial_size, bare_nucleoli, bland_chromatin, normal_nucleoli, mitoses]])
    result = model.predict(input_query)[0]
    return jsonify({'class': str(result)})

if __name__ == '__main__':
    app.run(debug=True)