from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from django.views.decorators.csrf import csrf_exempt
from rest_framework.renderers import JSONRenderer
from rest_framework.parsers import JSONParser
from bnb.models import BNBJob
from bnb.serializers import BNBJobSerializer


# Create your views here.
@csrf_exempt
def bnb_job_list(request):
    if request.method == 'GET':
        bnbs = BNBJob.objects.all()
        serializer = BNBJobSerializer(bnbs, many=True)
        return JsonResponse(serializer.data, safe=False)

    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = BNBJobSerializer(data=data)
        if serializer.is_valid():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)


@csrf_exempt
def bnb_job_detail(request, pk):
    try:
        bnb = BNBJob.objects.get(pk=pk)
    except BNBJob.DoesNotExist:
        return HttpResponse(status=404)

    if request.method == 'GET':
        serializer = BNBJobSerializer(bnb)
        return JsonResponse(serializer.data)

    elif request.method == 'PUT':
        data = JSONParser().parse(request)
        serializer = BNBJobSerializer(bnb, data=data)
        if serializer.is_valid():
            serializer.save()
            return JsonResponse(serializer.data)
        return JsonResponse(serializer.errors, status=400)

    elif request.method == 'DELETE':
        bnb.delete()
        return HttpResponse(status=204)
