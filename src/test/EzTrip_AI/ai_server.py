from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/recommend', methods=['POST'])
def recommend_course():
    data = request.get_json()
    station_name = data.get('stationName', 'Default Station')
    total_budget = data.get('totalBudget', 100000)
    preference = data.get('preference', 'A1B1C1D1E1')

    # 더미 AI 추천 결과
    recommended_course = "A1B2C1D1E1"
    course_detail = "Korean Food, Motel, Cafe, Museum, Park"
    total_price = 90000  # 더미 금액

    return jsonify({
        'courseDetail': course_detail,
        'totalPrice': total_price
    })

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=3309)