from rest_framework import serializers

from bnb.models import BNBJob


class BNBJobSerializer(serializers.Serializer):
    id = serializers.IntegerField(read_only=True)
    lower_bound = serializers.IntegerField(default='')

    def create(self, validated_data):
        return BNBJob.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.lower_bound = validated_data.get('lower_bound', instance.lower_bound)
        instance.save()
        return instance
