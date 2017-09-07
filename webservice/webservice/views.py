from .models import Subject
from django.http import JsonResponse
from django.shortcuts import get_object_or_404,  render

def index(request):
	return render(request, 'webservice/index.html', {}) 

def add_subject(request):
	subject = Subject();
	subject.tag_id = request.POST['tag_id']
	subject.start_time = request.POST['start_time']
	subject.save()

def update_subject(request, tag_id):
	subject = get_object_or_404(Subject, pk=tag_id)
	subject.endd_time.set(request.POST['end_time'])
	subject.save()

def get_latest_subjects(request):
	subjects = Subject.objects.all()
	data = []
	for obj in subjects:
		data.append({
			'tag_id': obj.tag_id,
			'name': obj.name,
			'description': obj.description,
			'start_time': obj.start_time,
			'end_time': obj.end_time
		})
	return JsonResponse(data, safe=False)