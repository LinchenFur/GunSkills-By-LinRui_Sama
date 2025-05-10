// MixinHumanoidModel.java
@Mixin(HumanoidModel.class)
public abstract class MixinHumanoidModel<T extends LivingEntity> {
    @Final
    @Shadow
    public ModelPart rightLeg;
    
    @Final
    @Shadow
    public ModelPart leftLeg;

    @Inject(method = "setupAnim", at = @At("TAIL"))
    private void modifySittingPose(T entity, float limbSwing, float limbSwingAmount, 
                                  float ageInTicks, float netHeadYaw, float headPitch, 
                                  CallbackInfo ci) {
        if (entity.getPose() == Pose.SITTING) {
            // 调整腿部角度
            rightLeg.xRot = -1.5F;
            leftLeg.xRot = -1.5F;
            // 调整身体角度
            ((LivingEntityAccessor)entity).setBodyYRot(0);
        }
    }
}